package com.project.obj;

public class UiConfig {
    private String uiBaseApi;

    public String getUiBaseApi() {
        return uiBaseApi;
    }

    public void setUiBaseApi(String uiBaseApi) {
        this.uiBaseApi = uiBaseApi;
    }

    @Override
    public String toString() {
        return "UiConfig{" +
                "uiBaseApi='" + uiBaseApi + '\'' +
                '}';
    }
}
