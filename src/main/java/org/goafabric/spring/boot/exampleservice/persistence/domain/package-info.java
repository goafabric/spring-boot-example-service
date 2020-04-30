@TypeDefs
        ({
                @TypeDef(
                        name="encryptedString",
                        typeClass= EncryptedStringType.class,
                        parameters= {
                                @Parameter(name="encryptorRegisteredName", value="hibernateStringEncryptor")
                        }
                ),
                @TypeDef(
                        name="encryptedSearchableString",
                        typeClass= EncryptedStringType.class,
                        parameters= {
                                @Parameter(name="encryptorRegisteredName", value="hibernateSearchableStringEncryptor")
                        }
                )

        })

package org.goafabric.spring.boot.exampleservice.persistence.domain;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.Parameter;
import org.jasypt.hibernate5.type.EncryptedStringType;