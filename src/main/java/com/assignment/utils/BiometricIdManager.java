package com.assignment.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class BiometricIdManager {

    private final Set<String> biometricIds;

    public BiometricIdManager() {
        biometricIds = new HashSet<>();
        biometricIds.add("K1YL8VA2HG");
        biometricIds.add("7DMPYAZAP2");
        biometricIds.add("D05HPPQNJ4");
        biometricIds.add("2WYIM3QCK9");
        biometricIds.add("DHKFIYHMAZ");
        biometricIds.add("LZK7P0X0LQ");
        biometricIds.add("H5C98XCENC");
        biometricIds.add("6X6I6TSUFG");
        biometricIds.add("QTLCWUS8NB");
        biometricIds.add("Y4FC3F9ZGS");
        biometricIds.add("V30EPKZQI2");
        biometricIds.add("O3WJFGR5WE");
        biometricIds.add("SEIQTS1H16");
        biometricIds.add("X16V7LFHR2");
        biometricIds.add("TLFDFY7RDG");
        biometricIds.add("PGPVG5RF42");
        biometricIds.add("FPALKDEL5T");
        biometricIds.add("2BIB99Z54V");
        biometricIds.add("ABQYUQCQS2");
        biometricIds.add("9JSXWO4LGH");
        biometricIds.add("QJXQOUPTH9");
        biometricIds.add("GOYWJVDA8A");
        biometricIds.add("6EBQ28A62V");
        biometricIds.add("30MY51J1CJ");
        biometricIds.add("FH6260T08H");
        biometricIds.add("JHDCXB62SA");
        biometricIds.add("O0V55ENOT0");
        biometricIds.add("F3ATSRR5DQ");
        biometricIds.add("1K3JTWHA05");
        biometricIds.add("FINNMWJY0G");
        biometricIds.add("CET8NUAE09");
        biometricIds.add("VQKBGSE3EA");
        biometricIds.add("E7D6YUPQ6J");
        biometricIds.add("BPX8O0YB5L");
        biometricIds.add("AT66BX2FXM");
        biometricIds.add("1PUQV970LA");
        biometricIds.add("CCU1D7QXDT");
        biometricIds.add("TTK74SYYAN");
        biometricIds.add("4HTOAI9YKO");
        biometricIds.add("PD6XPNB80J");
        biometricIds.add("BZW5WWDMUY");
        biometricIds.add("340B1EOCMG");
        biometricIds.add("CG1I9SABLL");
        biometricIds.add("49YFTUA96K");
        biometricIds.add("V2JX0IC633");
        biometricIds.add("C7IFP4VWIL");
        biometricIds.add("RYU8VSS4N5");
        biometricIds.add("S22A588D75");
        biometricIds.add("88V3GKIVSF");
        biometricIds.add("8OLYIE2FRC");
    }

    public synchronized boolean useBiometricId(String id) {
        return biometricIds.remove(id); // Removes and returns true if found; false otherwise
    }

    public Set<String> getRemainingBiometricIds() {
        return biometricIds;
    }
}
