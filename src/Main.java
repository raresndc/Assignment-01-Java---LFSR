import java.util.BitSet;

public class Main {
    public static void printRegister(int register) {
        System.out.println(
                "Register: " + Integer.toBinaryString(register));
        System.out.println(
                "Register: " + Integer.toHexString(register));
    }

    public static int initRegister(byte[] initialValues) {
        if (initialValues.length != 4) {
            System.out.println("Wrong initial value. 4 bytes needed");
            return 0;
        }
        int result = 0;
        for (int i = 3; i >= 0; i--) {
            result = result |
                    (((int) initialValues[3 - i] & 0xFF) << (i * 8));
        }
        return result;
    }

    public static byte applyTapSequence(int register) {
        byte result = 0;
        byte[] index = {31, 7, 5, 3, 2, 1, 0};
        for (int i = 0; i < index.length; i++) {
            byte bitValue = (byte) (((1 << index[i]) & register) >>> index[i]);
            result = (byte) (result ^ bitValue);
        }
        return result;
    }

    public static byte getLeastSignificantBit(int register) {
        return (byte) (register & 1);
    }

    public static int shiftAndInsertTapBit(int register, byte tapBit) {
        register = register >>> 1;
        register = register | (tapBit << 31);
        return register;
    }

    public static int fullStepInt(int register) {
        byte tapBit = applyTapSequence(register);
        register = shiftAndInsertTapBit(register, tapBit);
        return register;
    }

    public static byte[] generatePseudoRandomBytesInt(int register, int size) {
        byte[] result = new byte[size];
        for (int i = 0; i < size; i++) {
            byte generatedByte = 0;
            for (int j = 0; j < 8; j++) {
                generatedByte = (byte) ((generatedByte << 1) | getLeastSignificantBit(register));
                register = fullStepInt(register);
            }
            result[i] = generatedByte;
        }
        return result;
    }

    public static BitSet initRegisterBitSet(byte[] initialValues) {
        BitSet register = new BitSet(32);
        if (initialValues.length != 4) {
            System.out.println("Wrong initial value. 4 bytes needed");
            return register;
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                if ((initialValues[i] & (1 << (7 - j))) != 0) {
                    register.set(i * 8 + j);
                }
            }
        }
        return register;
    }

    public static boolean applyTapSequenceBitSet(BitSet register) {
        int[] taps = {31, 7, 5, 3, 2, 1, 0};
        boolean result = false;
        for (int tap : taps) {
            result ^= register.get(tap);
        }
        return result;
    }

    public static BitSet shiftAndInsertTapBitSet(BitSet register, boolean tapBit) {
        for (int i = 31; i > 0; i--) {
            register.set(i, register.get(i - 1));
        }
        register.set(0, tapBit);
        return register;
    }

    public static BitSet fullStepBitSet(BitSet register) {
        boolean tapBit = applyTapSequenceBitSet(register);
        return shiftAndInsertTapBitSet(register, tapBit);
    }

    public static byte[] generatePseudoRandomBytesBitSet(BitSet register, int size) {
        byte[] result = new byte[size];
        for (int i = 0; i < size; i++) {
            byte generatedByte = 0;
            for (int j = 0; j < 8; j++) {
                generatedByte = (byte) ((generatedByte << 1) | (register.get(0) ? 1 : 0));
                register = fullStepBitSet(register);
            }
            result[i] = generatedByte;
        }
        return result;
    }

    public static void printByteArray(byte[] array) {
        for (byte b : array) {
            System.out.print(String.format("%02X ", b));
        }
        System.out.println();
    }

    public static void main(String[] args) {

        byte[] seed = {(byte) 0b10101010, (byte) 0b11110000, (byte) 0b00001111, (byte) 0b01010101};

        int registerInt = initRegister(seed);
        System.out.println("Integer-based LFSR - 20 bytes:");
        byte[] randomBytesInt20 = generatePseudoRandomBytesInt(registerInt, 20);
        printByteArray(randomBytesInt20);

        System.out.println("Integer-based LFSR - 50 bytes:");
        byte[] randomBytesInt50 = generatePseudoRandomBytesInt(registerInt, 50);
        printByteArray(randomBytesInt50);

        BitSet registerBitSet = initRegisterBitSet(seed);
        System.out.println("BitSet-based LFSR - 20 bytes:");
        byte[] randomBytesBitSet20 = generatePseudoRandomBytesBitSet(registerBitSet, 20);
        printByteArray(randomBytesBitSet20);

        System.out.println("BitSet-based LFSR - 50 bytes:");
        byte[] randomBytesBitSet50 = generatePseudoRandomBytesBitSet(registerBitSet, 50);
        printByteArray(randomBytesBitSet50);

    }
}