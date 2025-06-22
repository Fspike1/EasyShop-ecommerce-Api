package org.yearup.data.mysql;

import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.yearup.data.mysql.MySqlProductDao.mapRow;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao {
    public MySqlCategoryDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories() {// get all categories
        List<Category> categories = new ArrayList<>();

        String sql = "SELECT * FROM categories;";
        try (
                Connection connection = getConnection();
                PreparedStatement psm = connection.prepareStatement(sql);
                ResultSet rs = psm.executeQuery()) {
            while (rs.next()) {
                Category category = mapRow(rs);
                categories.add(category);
            }


        } catch (SQLException e) {
            System.out.println("There was an error: " + e.getMessage());

        }
        return categories;
    }

    @Override
    public Category getById(int categoryId) {// get category by id
        String sql = "SELECT * FROM categories WHERE category_id = ?";
        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
                System.out.println("Found Category with ID: " + categoryId);
            return mapRow(rs);
        }
        catch (SQLException e){
            System.out.println("ERROR ALERT: " + e.getMessage());
    }
        return null;//if no match found
}

    @Override
    public Category create(Category category)
    {
        // create a new category
        return null;
    }

    @Override
    public void update(int categoryId, Category category)
    {
        // update category
    }

    @Override
    public void delete(int categoryId)
    {
        // delete category
    }

    private Category mapRow(ResultSet row) throws SQLException
    {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category()
        {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}
