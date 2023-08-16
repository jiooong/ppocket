package com.example.peeppo.domain.dibs.service;

import com.example.peeppo.domain.dibs.dto.DibsRequestDto;
import com.example.peeppo.domain.dibs.entity.Dibs;
import com.example.peeppo.domain.dibs.repository.DibsRepository;
import com.example.peeppo.domain.goods.entity.Goods;
import com.example.peeppo.domain.goods.repository.GoodsRepository;
import com.example.peeppo.domain.user.dto.CheckResponseDto;
import com.example.peeppo.domain.user.entity.User;
import com.example.peeppo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class DibsService {

    private final UserRepository userRepository;
    private final GoodsRepository goodsRepository;
    private final DibsRepository dibsRepository;

    public ResponseEntity<CheckResponseDto> dibsGoods(User user, DibsRequestDto dibsRequestDto) {
        findUser(user.getUserId());
        Goods goods = findGoods(dibsRequestDto.getGoodsId());

        Optional<Dibs> dibsGoods = dibsRepository.findByUserUserIdAndGoodsGoodsId(user.getUserId(), goods.getGoodsId());

        // 찜한 경우
        if (dibsGoods.isPresent()) {
            dibsRepository.delete(dibsGoods.get());
            CheckResponseDto response = new CheckResponseDto("이미 찜하신 목록입니다.", dibsGoods.isPresent(), OK.value(), "OK");
            return ResponseEntity.status(HttpStatus.OK.value()).body(response);
        }

        dibsRepository.save(new Dibs(goods, user));

        CheckResponseDto response = new CheckResponseDto("찜하셨습니다.", dibsGoods.isPresent(), OK.value(), "OK");
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 userId 입니다."));
    }

    public Goods findGoods(Long goodsId) {
        return goodsRepository.findById(goodsId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 goodsId 입니다."));
    }
}
