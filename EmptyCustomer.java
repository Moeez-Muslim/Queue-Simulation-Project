public class EmptyCustomer extends Customer {
    public EmptyCustomer() {
        super(-1, -1.0, () -> -1.0);
    }
}