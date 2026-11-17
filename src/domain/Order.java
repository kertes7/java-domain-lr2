package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Агрегат «Замовлення». Узгодженість суми тримається КОНСТРУКЦІЄЮ класу:
 * - внутрішній список прихований (private), назовні видається лише копія;
 * - сума обчислюється за позиціями (не зберігається окремим полем);
 * - позиції створюються лише тут після валідації.
 */
public class Order {

    private static int orderCounter = 0;

    private final int id;
    private final List<OrderItem> items = new ArrayList<>();   // прихований внутрішній набір

    public Order() {
        this.id = ++orderCounter;
    }

    public int getId() { return id; }

    /** Додавання з валідацією: некоректні дані не потрапляють в агрегат. */
    public boolean addItem(String name, double price, int quantity) {
        if (name == null || name.isBlank() || price <= 0 || quantity < 1) {
            return false;                       // відхилено
        }
        items.add(new OrderItem(name, price, quantity));
        return true;
    }

    /** Скорочена форма: кількість = 1. */
    public boolean addItem(String name, double price) {
        return addItem(name, price, 1);
    }

    /** Сума завжди відповідає поточному набору (обчислюється, не зберігається). */
    public double total() {
        double sum = 0;
        for (OrderItem it : items) {
            sum += it.lineTotal();              // службова операція узгодження (package-private)
        }
        return sum;
    }

    /** Назовні — лише захисна копія: зміни копії не впливають на агрегат. */
    public List<OrderItem> items() {
        return new ArrayList<>(items);
    }

    public int size() { return items.size(); }
}
