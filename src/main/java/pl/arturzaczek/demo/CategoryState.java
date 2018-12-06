package pl.arturzaczek.demo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryState {
    private boolean open;
    private boolean selected;
    private boolean disabled;
}