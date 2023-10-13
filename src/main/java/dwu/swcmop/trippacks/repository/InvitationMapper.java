package dwu.swcmop.trippacks.repository;

import dwu.swcmop.trippacks.dto.InvitationResponse;
import dwu.swcmop.trippacks.entity.Invitation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InvitationMapper {
    InvitationMapper INSTANCE = Mappers.getMapper(InvitationMapper.class);

    InvitationResponse invitationToInvitationDTO(Invitation invitation);
}
