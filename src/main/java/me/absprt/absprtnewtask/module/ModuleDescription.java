package me.absprt.absprtnewtask.module;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class ModuleDescription {
    private String name;
    private String author;
    private String version;
    private String main;
    private List<String> depends;
}
