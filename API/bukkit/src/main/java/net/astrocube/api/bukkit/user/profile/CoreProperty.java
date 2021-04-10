package net.astrocube.api.bukkit.user.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.codec.binary.Base64;

import javax.annotation.Nullable;
import java.security.*;

@Getter
@AllArgsConstructor
public class CoreProperty implements AbstractProperty {
    private final String name;
    private final String value;
    private final @Nullable String signature;

    public CoreProperty(String name, String value) {
        this.name = name;
        this.value = value;
        this.signature = null;
    }

    @Override
    public boolean hasSignature() {
        return signature != null;
    }

    @Override
    public boolean isSignatureValid(PublicKey key) {

        try {
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(key);
            signature.update(this.value.getBytes());
            return signature.verify(Base64.decodeBase64(this.signature));
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException ex) {
            ex.printStackTrace();
        }

        return false;
    }

}
