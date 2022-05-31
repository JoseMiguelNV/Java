public enum PaintingTypes {
    oilPainting,
    waterColour,
    pastel;

    //METHOD FOR CHECK IF EXISTS THE PAINTING INSERTED
    public static boolean ifExist(String paint){
        if(paint.equals(oilPainting.toString()) || paint.equals(waterColour.toString()) || paint.equals(pastel.toString())){
            return true;
        }
        return false;
    }
}
