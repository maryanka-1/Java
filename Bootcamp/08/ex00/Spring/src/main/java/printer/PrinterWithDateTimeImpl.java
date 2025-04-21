package printer;

import renderer.Renderer;

import java.time.LocalDateTime;

public class PrinterWithDateTimeImpl implements Printer {
    private Renderer renderer;
    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void print(String text) {
        renderer.render(text + LocalDateTime.now().toString());
    }
}
