package org.sparta.schedule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "refresh_token")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @Column(name = "user_id", nullable = false)
    private Long id;

    @OneToOne
    @MapsId //@Id로 지정된 컬럼에 @OneToOne 또는 @ManyToOne 관계 매핑
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    private String token;

    public void updateToken(String token) {
        this.token = token;
    }
}
