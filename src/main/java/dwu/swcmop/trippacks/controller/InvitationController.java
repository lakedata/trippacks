package dwu.swcmop.trippacks.controller;


import dwu.swcmop.trippacks.config.BaseException;
import dwu.swcmop.trippacks.config.BaseResponse;
import dwu.swcmop.trippacks.dto.InvitationRequest;
import dwu.swcmop.trippacks.dto.InvitationResponse;
import dwu.swcmop.trippacks.entity.Invitation;
import dwu.swcmop.trippacks.repository.InvitationMapper;
import dwu.swcmop.trippacks.service.InvitationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/invitations")
@Validated
public class InvitationController {

    private final InvitationService invitationService;

    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @Operation(summary = "초대링크 생성", description = "랜덤한 문자열로 초대링크를 생성합니다.")
    @PostMapping("")
    public ResponseEntity<BaseResponse<InvitationResponse>> createInvitation(
            @Valid @RequestBody InvitationRequest request
    ) throws BaseException {
        Invitation invitation = invitationService.createOrUpdate((long) request.getBagId());
        InvitationResponse invitationResponse = InvitationMapper.INSTANCE.invitationToInvitationDTO(invitation);
        invitationResponse.setBagId(Math.toIntExact(invitation.getBag().getBagId()));

        BaseResponse<InvitationResponse> response = new BaseResponse<>(invitationResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "초대링크 조회", description = "초대링크에 맞는 정보(bag)를 조회합니다.")
    @GetMapping("/{slug}")
    public ResponseEntity<BaseResponse<InvitationResponse>> getInvitation(@PathVariable String slug) throws BaseException {
        Invitation invitation = invitationService.findOneBySlug(slug);
        InvitationResponse invitationResponse = InvitationMapper.INSTANCE.invitationToInvitationDTO(invitation);
        invitationResponse.setBagId(Math.toIntExact(invitation.getBag().getBagId()));

        BaseResponse<InvitationResponse> response = new BaseResponse<>(invitationResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
