package app;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

import domain.Order;
import domain.OrderItem;

/**
 * Клас Main (пакет app). Наповнює агрегат і демонструє, що модель
 * не дозволяє привести його в некоректний стан ззовні.
 */
public class Main {

    public static void main(String[] args) {
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        Order order = new Order();
        order.addItem("Клавіатура", 1200.00, 1);
        order.addItem("Миша", 500.00, 2);

        out.println("Замовлення #" + order.getId());
        for (OrderItem it : order.items()) {
            out.println(it);
        }
        out.printf(Locale.US, "Разом: %.2f%n", order.total());

        // Спроба 1: змінити перелік позицій ззовні.
        List<OrderItem> external = order.items();   // лише копія
        external.clear();                           // очищаємо копію
        // external.add(new OrderItem(...));        // НЕ КОМПІЛЮЄТЬСЯ: конструктор OrderItem package-private
        out.printf(Locale.US, "спроба змінити перелік позицій ззовні -> на замовлення не впливає, Разом: %.2f%n",
                order.total());

        // Спроба 2: додати позицію з некоректною ціною.
        boolean ok = order.addItem("Товар", -10, 1);
        out.println("спроба додати позицію з ціною -10 -> " + (ok ? "додано" : "відхилено"));

        // Спроба 3: службова операція узгодження недоступна поза пакетом domain.
        // double x = order.items().get(0).lineTotal(); // НЕ КОМПІЛЮЄТЬСЯ: lineTotal() package-private

        out.printf(Locale.US, "Сума залишається узгодженою: позицій=%d, Разом: %.2f%n",
                order.size(), order.total());
    }
}
