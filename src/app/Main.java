package app;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import domain.Cart;
import domain.CartItem;

/**
 * Клас Main (пакет app). Наповнює агрегат і демонструє, що модель
 * не дозволяє привести його в некоректний стан ззовні.
 */
public class Main {

    public static void main(String[] args) {
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        Cart cart = new Cart();
        cart.addItem("Клавіатура", 1200.00, 1);
        cart.addItem("Миша", 500.00, 2);

        out.println("Кошик #" + cart.getId());
        for (CartItem it : cart.items()) {
            out.println(it);
        }
        out.printf(java.util.Locale.US, "Разом: %.2f%n", cart.total());

        // Спроба 1: змінити перелік позицій ззовні.
        List<CartItem> external = cart.items();   // лише копія
        external.clear();                         // очищаємо копію
        // external.add(new CartItem(...));       // НЕ КОМПІЛЮЄТЬСЯ: конструктор CartItem package-private
        // cart.items — приватне поле, недоступне напряму
        out.printf(java.util.Locale.US, "спроба змінити перелік позицій ззовні -> на кошик не впливає, Разом: %.2f%n",
                cart.total());

        // Спроба 2: додати позицію з некоректною ціною.
        boolean ok = cart.addItem("Товар", -10, 1);
        out.println("спроба додати позицію з ціною -10 -> " + (ok ? "додано" : "відхилено"));

        // Спроба 3: службова операція узгодження недоступна поза пакетом domain.
        // double x = cart.items().get(0).lineTotal(); // НЕ КОМПІЛЮЄТЬСЯ: lineTotal() package-private

        out.printf(java.util.Locale.US, "Підсумок залишається узгодженим: позицій=%d, Разом: %.2f%n",
                cart.size(), cart.total());
    }
}
