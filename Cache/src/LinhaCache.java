public class LinhaCache {
    //ATRIBUTOS
    private int tag;
    private boolean bitValidade;

    //CONSTRUTOR
    public LinhaCache() {
        this.bitValidade = false;
        this.tag = -1; // -1 para indicar que a tag nao esta definida no inicio.
    }

    //MÉTODOS
    public int getTag() {
        return this.tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public boolean isValida() {
        return this.bitValidade;
    }

    public void setValida(boolean valida) {
        this.bitValidade = valida;
    }

    public void invalidar() {
        this.bitValidade = false;
    }
}