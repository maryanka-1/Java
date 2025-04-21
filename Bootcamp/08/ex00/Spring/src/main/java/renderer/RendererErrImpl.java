package renderer;

import preprocessor.PreProcessor;

public class RendererErrImpl implements Renderer{

    private PreProcessor preProcessor;
    public RendererErrImpl(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    @Override
    public void render(String text) {
        System.err.println(preProcessor.change(text));
    }
}
