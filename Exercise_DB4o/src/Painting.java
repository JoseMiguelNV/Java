import java.time.LocalDate;

public class Painting extends ArtWork{

    protected PaintingTypes type;
    protected DimensionsType dimensions;

    public Painting(){

    }

    public Painting(int codeArtWork, String title, LocalDate dated, Styles style, String author, PaintingTypes type, DimensionsType dimensions) {
        super(codeArtWork, title, dated, style, author);
        this.type = type;
        this.dimensions = dimensions;
    }

    public Painting(ArtWork enterDataArtWork) {

    }

    public PaintingTypes getType() {
        return type;
    }

    public void setType(PaintingTypes type) {
        this.type = type;
    }

    public DimensionsType getDimensions() {
        return dimensions;
    }

    public void setDimensions(DimensionsType dimensions) {
        this.dimensions = dimensions;
    }

    @Override
    public String toString() {
        return "Painting{" +
                "codeArtWork=" + codeArtWork +
                ", title='" + title + '\'' +
                ", dated=" + dated +
                ", style=" + style +
                ", codeAuthor='" + codeAuthor + '\'' +
                ", type=" + type +
                ", dimensions=" + dimensions +
                '}';
    }
}
