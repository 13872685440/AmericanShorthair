package com.cat.dictionary.enumable;

public enum CantonLevel
{

    国家级("国家级", "001.jpg",""), 省级("省级", "002.jpg",""),直辖市("直辖市", "003.jpg",""), 地级市("地级市", "003.jpg",""), 州("州", "003.jpg",""),盟("盟", "003.jpg",""), 省直管(
            "省直管", "004.jpg",""), 城区("城区", "004.jpg",""), 区("区", "005.jpg","中心城区"), 县("县", "006.jpg","县市区"), 直管县(
            "直管县", "006.jpg",""), 县级市("县级市", "004.jpg",""), 旗("旗", "006.jpg",""), 农场("农场", "007.jpg",""), 街道(
            "街道", "007.jpg",""), 镇("镇", "007.jpg",""), 乡("乡", "008.jpg",""), 居委会("居委会", "009.jpg",""), 村委会(
            "村委会", "009.jpg","");

    private final String desc;
    
    private final String icon;

    private final String ms;
    
    private CantonLevel(String desc, String icon,String ms)
    {
        this.desc = desc;
        this.icon = icon;
        this.ms = ms;
    }


    public String getDesc()
    {
        return desc;
    }


    public String getIcon()
    {
        return icon;
    }
    public static CantonLevel byName(String name)
	{
		if (name == null)
			return null;
		for (int j = 0; j < CantonLevel.values().length; j++)
		{
			CantonLevel lb = CantonLevel.values()[j];
			if (lb.name().equals(name))
			{
				return lb;
			}
		}
		return null;
	}
    
    public static CantonLevel byMs(String name)
	{
		if (name == null)
			return null;
		for (int j = 0; j < CantonLevel.values().length; j++)
		{
			CantonLevel lb = CantonLevel.values()[j];
			if (lb.getMs().equals(name))
			{
				return lb;
			}
		}
		return null;
	}

	public String getMs() {
		return ms;
	}

}
