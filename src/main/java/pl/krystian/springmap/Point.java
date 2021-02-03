package pl.krystian.springmap;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Point {
    private double x;
    private double y;
    private String text;
}
