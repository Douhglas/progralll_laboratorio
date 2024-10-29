package cr.ac.una.gmailapp.model;

import java.util.Objects;

/**
 *
 * @author stward segura
 */
public class Admin {
    
    private Long id;
    private String email;
    private String passWord;

    public Admin() {
        id = null;
        email = "";
        passWord = "";
    }
 
    public Admin(Long id) {
        this.id = id;
    }
 
    public Long getAdminId() {
        return id;
    }

    public void setAdminId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return passWord;
    }

    public void setPassword(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Admin  adminDto = (Admin ) obj;
        return Objects.equals(id, adminDto.id);
    }
}

