package fr.android.fcmetrics.modules;

public class UserEntity {
    private String mail;
    private String password;
    private String _id;
    private String name;
    private String __v;

    public UserEntity(String mail, String password, String _id, String name, String __v) {
        this.mail = mail;
        this.password = password;
        this._id = _id;
        this.name = name;
        this.__v = __v;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }
}
