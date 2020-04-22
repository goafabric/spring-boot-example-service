package org.goafabric.spring.boot.exampleservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
public class StreetSplitTest {
    @Test
    public void test() {
        assertThat(splitStreet("am Kürchen 5").group(1)).isEqualTo("am Kürchen");
        assertThat(splitStreet("am Kürchen 5").group(2)).isEqualTo("5");

        assertThat(splitStreet("Fichtenstraße 10-23a").group(1)).isEqualTo("Fichtenstraße");
        assertThat(splitStreet("Fichtenstraße 10-23a").group(2)).isEqualTo("10-23a");

        assertThat(splitStreet("Name der Strase 25a").group(1)).isEqualTo("Name der Strase");
        assertThat(splitStreet("Name der Strase 25a").group(2)).isEqualTo("25a");

        //assertThat(splitStreet("Straße des 17. Juni 23a").group(1)).isEqualTo("Straße des 17. Juni");
        //assertThat(splitStreet("Straße des 17. Juni 23a").group(1)).isEqualTo("23a");
    }


    private Matcher splitStreet(final String street) {
        final Pattern pattern = Pattern.compile("^((?:\\p{L}| |\\d|\\.|-)+?) (\\d+(?: ?- ?\\d+)? *[a-zA-Z]?)");
        final Matcher matcher = pattern.matcher(street);
        if (!matcher.find() || matcher.groupCount() != 2) throw new IllegalStateException("street could not be split: " + street);
        log.info(matcher.group(1));log.info(matcher.group(2));
        return matcher;
    }
}
