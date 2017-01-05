package com.pizza73.model.enums;

/**
 * StreetType.java TODO comment me
 * 
 * @author chris 23-Jan-07
 * 
 * @Copyright Flying Pizza 73
 */
public enum StreetType
{
    ST("ST"), AVE("AVE"), RD("RD"), DR("DR"), CR("CR"), PL("PL"), WAY("WAY"), CL("CL"), BLVD("BLVD"), CT("CT"), CIR("CIR"), BAY(
        "BAY"), GN("GN"), TERR("TERR"), RISE("RISE"), LN("LN"), PK("PK"), TR("TR"), MNR("MNR"), GDNS("GDNS"), VIEW("VIEW"), PT(
        "PT"), HTS("HTS"), HILL("HILL"), MEWS("MEWS"), GRV("GRV"), GT("GT"), LI("LI"), CV("CV"), LANDING("LANDNG"), COMMON(
        "COMMON"), WYND("WYND"), SQ("SQ"), VILLAS("VILLAS"), VILL("VILL"), HWY("HWY"), HEATH("HEATH"), CAMPUS("CAMPUS"), LP(
        "LP"), BEND("BEND"), PARADE("PARADE"), EST("EST"), RI("RI"), MT("MT"), PLAZA("PLAZA"), GLEN("GLEN"), ROW("ROW"), RUN(
        "RUN"), WALK("WALK"), CAPE("CAPE"), CTR("CTR"), MALL("MALL"), HARBR("HARBR"), CROSS("CROSS"), INLET("INLET"), ACRES(
        "ACRES"), KEY("KEY"), HOLLOW("HOLLOW"), PATH("PATH"), VISTA("VISTA"), KNOLL("KNOLL"), MAZE("MAZE"), TOWERS("TOWERS"), ALLEY(
        "ALLEY"), DOWNS("DOWNS"), END("END"), ISLAND("ISLAND"), MDW("MDW"), RAMP("RAMP");

    private String type;

    StreetType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return this.type;
    }

    public static String[] typeValues()
    {
        StreetType[] values = StreetType.values();
        String[] typeValues = new String[values.length];
        for (int i = 0; i < values.length; i++)
        {
            typeValues[i] = values[i].getType();
        }

        return typeValues;
    }
}
