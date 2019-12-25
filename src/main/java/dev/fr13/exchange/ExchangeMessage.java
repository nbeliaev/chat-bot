package dev.fr13.exchange;

public class ExchangeMessage {
    private final String id;
    private final String data;

    public ExchangeMessage(String id, String data) {
        this.id = id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExchangeMessage that = (ExchangeMessage) o;

        if (!id.equals(that.id)) return false;
        return data.equals(that.data);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + data.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ExchangeMessage{" +
                "id='" + id + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
