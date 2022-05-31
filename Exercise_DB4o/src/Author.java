

public class Author {

    protected String codeAuthor;
    protected String nameAuthor;
    protected String nationality;

    public Author(){
    }

    public Author(String codeAuthor, String nameAuthor, String nationality){
        this.codeAuthor = codeAuthor;
        this.nameAuthor = nameAuthor;
        this.nationality = nationality;
    }


    public String getCodeAuthor() {
        return codeAuthor;
    }

    public void setCodeAuthor(String codeAuthor) {
        this.codeAuthor = codeAuthor;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public boolean equalIs( Object other ) {
        if ( other == this )
            return true;
        if ( !(other instanceof Author) )
            return false;
        Author otherAuthor = (Author)other;
        return (this.codeAuthor.equals(otherAuthor.codeAuthor) &&
                this.nameAuthor.equals(otherAuthor.nameAuthor) &&
                this.nationality == otherAuthor.nationality);
    }

    @Override
    public String toString() {
        return "Author{" +
                "codeAuthor='" + codeAuthor + '\'' +
                ", nameAuthor='" + nameAuthor + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}

