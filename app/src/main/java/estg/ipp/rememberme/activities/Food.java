package estg.ipp.rememberme.activities;

class Food {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Food{" +
                "code='" + code + '\'' +
                '}';
    }
}
