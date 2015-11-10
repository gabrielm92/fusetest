package com.test.android.fusetest.dal.models.communication.response;

public class CompanyResponseModel {
    private String name;

    private String logo;

    public String getLogo() {
        return logo;
    }

    public String getName() {
        return name;
    }

    public CompanyResponseModel setName(String name) {
        this.name = name;

        return this;
    }

    public CompanyResponseModel setLogo(String logo) {
        this.logo = logo;

        return this;
    }
}
