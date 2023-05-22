import java.util.List;
import lombok.Data;


@Data
public class TreeNode {
    private Long id;

    private Long parentId;

    private List<TreeNode> children;
}