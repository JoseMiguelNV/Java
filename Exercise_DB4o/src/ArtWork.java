import java.time.LocalDate;

public class ArtWork {
    protected int codeArtWork;
    protected String title;
    protected LocalDate dated;
    protected Styles style;
    protected String codeAuthor;

    public ArtWork(){
    }

    public ArtWork(int codeArtWork, String title, LocalDate dated, Styles style, String codeAuthor) {
        this.codeArtWork = codeArtWork;
        this.title = title;
        this.dated = dated;
        this.style = style;
        this.codeAuthor = codeAuthor;
    }

    public int getCodeArtWork() {
        return codeArtWork;
    }

    public void setCodeArtWork(int codeArtWork) {
        this.codeArtWork = codeArtWork;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDated() {
        return dated;
    }

    public void setDated(LocalDate dated) {
        this.dated = dated;
    }

    public Styles getStyle() {
        return style;
    }

    public void setStyle(Styles style) {
        this.style = style;
    }

    public String getAuthor() {
        return codeAuthor;
    }

    public void setAuthor(String author) {
        this.codeAuthor = author;
    }

    @Override
    public String toString() {
        return "ArtWork{" +
                "codeArtWork=" + codeArtWork +
                ", title='" + title + '\'' +
                ", dated=" + dated +
                ", style=" + style +
                ", codeAuthor='" + codeAuthor + '\'' +
                '}';
    }
}
