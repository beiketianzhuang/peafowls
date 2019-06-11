package com.lchen.ccdeploy.model;

import com.lchen.ccdeploy.model.constants.ContextType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author : lchen
 * @date : 2019/6/11
 */
@Entity
@Data
public class Context {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String context;
    @Enumerated(EnumType.STRING)
    private ContextType contextType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
