package preprocessor;

public class PreProcessorToUpperImpl implements PreProcessor {
    @Override
    public String change(String text) {
        return text.toUpperCase();
    }
}
