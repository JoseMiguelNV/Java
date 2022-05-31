public enum MaterialTypes {
    iron,
    bronze,
    marble;

    //METHOD FOR CHECK IF EXISTS THE MATERIAL TYPE INSERTED
    public static boolean ifExist(String material){
        if(material.equals(iron.toString()) || material.equals(bronze.toString()) || material.equals(marble.toString())){
            return true;
        }
        return false;
    }
}
