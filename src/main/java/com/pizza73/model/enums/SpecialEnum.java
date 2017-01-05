package com.pizza73.model.enums;

public enum SpecialEnum
{
    FOUR_PAK(Integer.valueOf(37)),
    EVERYDAY_DEAL(Integer.valueOf(223)),
    WING_BOX(Integer.valueOf(224)),
    MEGA_MEAL_12(Integer.valueOf(312)),
    MEGA_MEAL_14(Integer.valueOf(313)),
    SINGLE_TWO_TOPPER_PLUS_ONE_LITRE(Integer.valueOf(363)),
    HOLIDAY_HELPER(Integer.valueOf(378)),
    SINGLE_HAWAIIAN_PLUS_ONE_LITRE(Integer.valueOf(452)),
    SINGLE_VALENTINES(Integer.valueOf(472)),
    STAMPEDE_FOUR_PAK(Integer.valueOf(474)),
    SLICES_FOR_SMILES_DONATION(Integer.valueOf(496)),
    GLUTEN_FREE(Integer.valueOf(497)),
    SINGLE_73_DELUXE(Integer.valueOf(516)),
    WING_IT_BOX(Integer.valueOf(510)),
    OKOTOKS_JAN_2013(Integer.valueOf(519)),
    SLICES_FOR_SMILES_499(Integer.valueOf(530)),
    HH_SWEET_CHILI_BONELESS(Integer.valueOf(531)),
    HH_FOUR_CAN_POP(Integer.valueOf(532)),
    SINGLE_MOTHERS_DAY(Integer.valueOf(536)),
    BONELESS_BOX(Integer.valueOf(547)),
    TWO_TOPPER_GLUTTEN_FREE_SP(Integer.valueOf(560)),
    POP_FOUR_PAK(Integer.valueOf(540)),
    CHICKEN_BITES(Integer.valueOf(559)),
    AFTER_SCHOOL(Integer.valueOf(569)),
    LUNCH_COMBO(Integer.valueOf(570)),
    TWO_DIPS(Integer.valueOf(571)),
    HOLIDAY_PARTY_PACK(Integer.valueOf(582)),
    CHILLI_COMBO(Integer.valueOf(583)),
    POUTINE_COMBO(Integer.valueOf(584)),
    SMALL_TWO_TOPPER_COMBO(Integer.valueOf(591)),
    DINNER_MOVIE_SMALL(Integer.valueOf(592)),
    DINNER_MOVIE_MEDIUM(Integer.valueOf(593)),
    DINNER_MOVIE_LARGE(Integer.valueOf(594)),
    POUTINE_MOVIE(Integer.valueOf(595)),
    CHILI_MOVIE(Integer.valueOf(596)),
    WEDGIE_MOVIE(Integer.valueOf(597)),
    SINGLE_CHILI_PLUS_ONE_LITRE(Integer.valueOf(600)),
    TASTY_22(Integer.valueOf(602)),
    SINGLE_PESTO_GARDEN_VEGGIE_PLUS_ONE_LITRE(Integer.valueOf(604)),
    TASTY_20(Integer.valueOf(606)),
    TERRIFIC_TWO(Integer.valueOf(621)),
    BIG_TASTE(Integer.valueOf(622)),
    FAN_FAVOURITE(Integer.valueOf(640)),
    VALENTINES_DELIGHT(Integer.valueOf(647)),
    D_AND_M(Integer.valueOf(648)),
    PLENTY_FOR_20(Integer.valueOf(649));

    private Integer id;

    SpecialEnum(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return this.id;
    }

    public static SpecialEnum specialForId(Integer id)
    {
        SpecialEnum pc = null;
        SpecialEnum[] specials = SpecialEnum.values();
        for (SpecialEnum special : specials)
        {
            if (special.getId().equals(id))
            {
                pc = special;
                break;
            }
        }

        return pc;
    }

    public boolean isEqualToSpecialId(Integer specialId)
    {
        return null != specialId ? this.id.equals(specialId) : false;
    }
}
