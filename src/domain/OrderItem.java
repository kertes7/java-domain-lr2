package domain;

/**
 * Елемент агрегата (варіант 0 — позиція замовлення).
 * Незмінні дані (id, name, price) — final; кількість змінюється контрольовано.
 * Конструктор package-private: створити позицію можна лише через Order,
 * який валідує дані — отже некоректні елементи не потраплять в агрегат.
 */
public class OrderItem {

    private static int counter = 0;     // спільний лічильник -> унікальні номери

    private final int id;               // унікальний, не змінюється
    private final String name;          // незмінне після створення
    private final double price;         // незмінне після створення
    private int quantity;               // змінюється лише контрольовано

    /** Повний набір даних (package-private — лише для класів домену). */
    OrderItem(String name, double price, int quantity) {
        this.id = ++counter;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    /** Скорочений: кількість за замовчуванням = 1 (делегує через this(...)). */
    OrderItem(String name, double price) {
        this(name, price, 1);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    /** Контрольована зміна кількості: некоректне значення відхиляється. */
    public boolean setQuantity(int q) {
        if (q < 1) return false;
        this.quantity = q;
        return true;
    }

    /** Службова операція узгодження — доступна лише в межах пакета domain. */
    double lineTotal() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return String.format(java.util.Locale.US, "+ %s %.2f x %d", name, price, quantity);
    }
}
