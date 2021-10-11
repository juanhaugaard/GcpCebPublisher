public class IncomingItemDTO {
    private String item;
    private int quantity;

    public IncomingItemDTO() {
    }

    public String getItem() {
        return this.item;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof IncomingItemDTO)) {
            return false;
        }
        final IncomingItemDTO other = (IncomingItemDTO) o;
        if (!other.canEqual((Object) this)) {
            return false;
        }
        final Object this$item = this.getItem();
        final Object other$item = other.getItem();
        if (this$item == null ? other$item != null : !this$item.equals(other$item)) {
            return false;
        }
        if (this.getQuantity() != other.getQuantity()) {
            return false;
        }
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof IncomingItemDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $item = this.getItem();
        result = result * PRIME + ($item == null ? 43 : $item.hashCode());
        result = result * PRIME + this.getQuantity();
        return result;
    }

    public String toString() {
        return "IncomingItemDTO(item=" + this.getItem() + ", quantity=" + this.getQuantity() + ")";
    }
}
