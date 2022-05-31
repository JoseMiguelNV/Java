public enum Styles {
    grecoRoman,
    neoClassic,
    Cubism;

    //METHOD FOR CHECK IF EXISTS THE STYLE INSERTED
    public static boolean ifExist(String style){
        if(style.equals(grecoRoman.toString()) || style.equals(neoClassic.toString()) || style.equals(Cubism.toString())){
            return true;
        }
        return false;
    }
}

