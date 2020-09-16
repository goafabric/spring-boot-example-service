package org.goafabric.spring.boot.exampleservice.configuration;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TestCLass {
    private final String[] statements = new String[]{
            "create schema if not exists sync",
            "create table if not exists sync.sync_changes ("
                    + "  id bigserial primary key,"
                    + "  table_name varchar not null,"
                    + "  row_id varchar not null,"
                    + "  operation varchar not null,"
                    + "  ts timestamp default now() not null,"
                    + "  tx_id bigint not null"
                    + ")",
            "CREATE OR REPLACE FUNCTION sync_changes() RETURNS trigger AS $$"
                    + "DECLARE"
                    + "  rec RECORD;"
                    + "BEGIN"
                    + "  IF (tg_op = 'DELETE') THEN rec = OLD; ELSE rec = NEW; END IF;"
                    + ""
                    + "  INSERT INTO sync.sync_changes (table_name, row_id, operation, tx_id)"
                    + "  VALUES (TG_RELNAME, rec.id, TG_OP, txid_current());"
                    + ""
                    + "  IF (tg_op = 'DELETE') THEN"
                    + "    RETURN OLD;"
                    + "  ELSE"
                    + "    RETURN NEW;"
                    + "  END IF;"
                    + "END;"
                    + "$$ LANGUAGE 'plpgsql'",
            "CREATE TRIGGER patient_sync_trigger"
                    + "  AFTER INSERT OR UPDATE OR DELETE ON bas_patient"
                    + "  FOR EACH ROW EXECUTE PROCEDURE sync_changes()",
            "CREATE TRIGGER person_sync_trigger"
                    + "  AFTER INSERT OR UPDATE OR DELETE ON bas_person"
                    + "  FOR EACH ROW EXECUTE PROCEDURE sync_changes()",
            "CREATE TRIGGER telecom_sync_trigger"
                    + "  AFTER INSERT OR UPDATE OR DELETE ON bas_telecom"
                    + "  FOR EACH ROW EXECUTE PROCEDURE sync_changes()",
            "update bas_patient set version = version",
            "update bas_telecom set version = version;"
    };

    private final String[] statements2 = new String[]{
            // create trigger
            "CREATE TRIGGER ais_mrc_entry_type_config_trigger"
                    + "  AFTER INSERT OR UPDATE OR DELETE ON ais_mrc_entry_type_config"
                    + "  FOR EACH ROW EXECUTE PROCEDURE sync_changes()",
            "CREATE TRIGGER ais_mrc_container_config_trigger"
                    + "  AFTER INSERT OR UPDATE OR DELETE ON ais_mrc_container_config"
                    + "  FOR EACH ROW EXECUTE PROCEDURE sync_changes()",
            "CREATE TRIGGER ais_mrc_config_assoc_trigger"
                    + "  AFTER INSERT OR UPDATE OR DELETE ON ais_mrc_config_assoc"
                    + "  FOR EACH ROW EXECUTE PROCEDURE sync_changes()",
            "CREATE OR REPLACE FUNCTION sync_changes_association() RETURNS trigger AS $$"
                    + " BEGIN"
                    + "   IF (tg_op = 'DELETE') THEN"
                    + "     INSERT INTO sync.sync_changes (table_name, row_id, operation, tx_id)"
                    + "     VALUES ('ais_mrc_entry_type_config', OLD.entrytypeconfigid, 'UPDATE', txid_current());"
                    + "     RETURN OLD;"
                    + "   ELSE"
                    + "     INSERT INTO sync.sync_changes (table_name, row_id, operation, tx_id)"
                    + "     VALUES ('ais_mrc_entry_type_config', NEW.entrytypeconfigid, 'UPDATE', txid_current());"
                    + "     RETURN NEW;"
                    + "   END IF;"
                    + ""
                    + " END;"
                    + " $$ LANGUAGE 'plpgsql'",
            "CREATE TRIGGER config_association_sync_trigger "
                    + "  AFTER INSERT OR UPDATE OR DELETE ON ais_mrc_config_assoc "
                    + "  FOR EACH ROW EXECUTE PROCEDURE sync_changes_association()",
            // force sync to helios
            "update ais_mrc_container_config set deleted = deleted",
            "update ais_mrc_entry_type_config set contenttype = contenttype",
            "update ais_med_prescription u set adminmedicationinformationid = adm.versionindependentid "
                    + "from ais_med_prescription pres "
                    + "join ais_med_admin_information adm on pres.adminmedicationinformationid = adm.id "
                    + "where u.id = pres.id"
    };

    @Test
    public void test() {
        List<String> stats = Arrays.asList(statements2);
        for (String s : stats) {
            System.out.println(s + ";\n");
        }
    }
}
