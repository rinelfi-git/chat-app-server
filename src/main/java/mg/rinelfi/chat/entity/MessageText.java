/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.rinelfi.chat.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author rinelfi
 */
@Entity(name = "MessageText")
public class MessageText extends Message implements Serializable {

    @Column(name = "tm_content", length = 65536)
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
