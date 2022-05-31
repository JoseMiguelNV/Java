import java.time.LocalDate;

public class Sculpture extends ArtWork{

    protected MaterialTypes material;
    protected double weigth;


    public Sculpture(int codeArtWork, String title, LocalDate dated, Styles style, String author, MaterialTypes material, double weigth) {
        super(codeArtWork, title, dated, style, author);
        this.material = material;
        this.weigth = weigth;
    }

    public MaterialTypes getMaterial() {
        return material;
    }

    public void setMaterial(MaterialTypes material) {
        this.material = material;
    }

    public double getWeigth() {
        return weigth;
    }

    public void setWeigth(double weigth) {
        this.weigth = weigth;
    }

    @Override
    public String toString() {
        return "Sculpture{" +
                "codeArtWork=" + codeArtWork +
                ", title='" + title + '\'' +
                ", dated=" + dated +
                ", style=" + style +
                ", codeAuthor='" + codeAuthor + '\'' +
                ", material=" + material +
                ", weigth=" + weigth +
                '}';
    }
}
