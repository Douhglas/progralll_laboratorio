package cr.ac.una.gmailapp.model;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author stward segura
 */
public class SenderDto {

    private Long senderId;
    private Integer emailsperHour;
    private Long seconds;
    private Integer hour;
    private Integer minutes;
    private ObservableList<CorreoDto> tarCorreoList;

    public SenderDto() {
        this.hour = 0;
        this.minutes = 0;
        this.senderId = null;
        this.emailsperHour = 0;
        this.seconds = 0L;
        this.tarCorreoList = FXCollections.observableArrayList();
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Integer getEmailsperhour() {
        return emailsperHour;
    }

    public void setEmailsperhour(Integer emailsPerHour) {
        this.emailsperHour = emailsPerHour;
    }

    public Long getSeconds() {
        return seconds;
    }

    public void setSeconds(Long seconds) {
        this.seconds = seconds;
    }

    public List<CorreoDto> getTarCorreoList() {
        return new ArrayList<>(tarCorreoList);
    }

    public void setTarCorreoList(List<CorreoDto> tarCorreoList) {
        this.tarCorreoList = FXCollections.observableArrayList(tarCorreoList);
    }

    public void addToList(CorreoDto dto) {
        this.tarCorreoList.add(dto);
    }

    public Integer getHour() {
        return this.hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinutes() {
        return this.minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    
}
