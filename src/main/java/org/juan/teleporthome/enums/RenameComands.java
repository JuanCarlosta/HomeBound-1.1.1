package org.juan.teleporthome.enums;

public enum RenameComands {
    RENAME,
    LORE,
    REMOVENAME,
    REMOVELORE;

    public static String getStringName (RenameComands rc){
        switch (rc){
            case RENAME:{
                return "rename";
            }
            case LORE:{
                return "lore";
            }
            case REMOVENAME:{
                return "removename";
            }
            case REMOVELORE: {
                return "removelore";
            }
        }
        return "Command doesn't exist";
    }

}
