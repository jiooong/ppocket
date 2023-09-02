package com.example.peeppo.domain.chat.entity;

import com.example.peeppo.domain.goods.entity.Goods;
import com.example.peeppo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserChatRoomRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "user_Id", nullable = false)
    private User seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id",  referencedColumnName = "user_Id", nullable = false)
    private User buyer;

    private String sessionId;


    @ManyToOne
    @JoinColumn(name = "chatRoom_id", nullable = false)
    private ChatRoom chatRoom;

    public UserChatRoomRelation(ChatRoom chatRoom, User user, String sessionId) {
        this.chatRoom = chatRoom;
        this.seller = chatRoom.getGoods().getUser();
        this.buyer = user;
        this.sessionId = sessionId;
    }

    public UserChatRoomRelation(User user, ChatRoom chatRoom, Goods goods) {
        this.chatRoom = chatRoom;
        this.seller = goods.getUser();
        this.buyer = user;
    }


}