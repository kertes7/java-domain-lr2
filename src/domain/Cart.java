package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Агрегат «Кошик». Узгодженість підсумку тримається КОНСТРУКЦІЄЮ класу:
 * - внутрішній список прихований (private), назовні видається лише копія;
 * - підсумок обчислюється за елементами (не зберігається окремим полем);
 * - елементи створюються лише тут після валідації.
 */
public class Cart {

    private static int cartCounter = 0;

    private final int id;
    private final List<CartItem> items = new ArrayList<>();   // прихований внутрішній набір

    public Cart() {
        this.id = ++cartCounter;
    }

    public int getId() { return id; }

    /** Додавання з валідацією: некоректні дані не потрапляють в агрегат. */
    public boolean addItem(String name, double price, int quantity) {
        if (name == null || name.isBlank() || price <= 0 || quantity < 1) {
            return false;                       // відхилено
        }
        items.add(new CartItem(name, price, quantity));
        return true;
    }

    /** Скорочена форма: кількість = 1. */
    public boolean addItem(String name, double price) {
        return addItem(name, price, 1);
    }

    /** Підсумок завжди відповідає поточному набору (обчислюється, не зберігається). */
    public double total() {
        double sum = 0;
        for (CartItem it : items) {
            sum += it.lineTotal();              // службова операція узгодження (package-private)
        }
        return sum;
    }

    /** Назовні — лише захисна копія: зміни копії не впливають на агрегат. */
    public List<CartItem> items() {
        return new ArrayList<>(items);
    }

    public int size() { return items.size(); }
}
