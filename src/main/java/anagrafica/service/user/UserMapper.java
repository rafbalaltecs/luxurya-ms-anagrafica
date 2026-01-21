package anagrafica.service.user;

import anagrafica.dto.user.TypeUserRequest;
import anagrafica.dto.user.TypeUserResponse;
import anagrafica.entity.TypeUser;
import anagrafica.exception.RestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("UserMapper")
@Slf4j
public class UserMapper {

    public TypeUser requestToEntityTypeUser(final TypeUserRequest request){
        if(request != null){
            final TypeUser typeUser = new TypeUser();
            typeUser.setCode(request.getCode());
            typeUser.setDescription(request.getDescription());
            typeUser.setName(request.getName());
            typeUser.setDeleted(Boolean.FALSE);
            return typeUser;
        }
        log.error("Not Mapping Request To Entity: {}", request);
        throw new RestException("Not Mapping Request To Entity - Request is null");
    }

    public TypeUserResponse entityToDTOTypeUser(final TypeUser typeUser){
        if(typeUser != null){
            final TypeUserResponse typeUserResponse = new TypeUserResponse();
            typeUserResponse.setCode(typeUser.getCode());
            typeUserResponse.setName(typeUser.getName());
            typeUserResponse.setId(typeUser.getId());
            typeUserResponse.setDescription(typeUser.getDescription());
            return typeUserResponse;
        }
        log.error("Not Mapping Entity: {}", typeUser);
        return null;
    }

}
