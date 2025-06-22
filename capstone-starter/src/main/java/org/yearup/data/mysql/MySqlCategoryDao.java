package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    {// create a new category
       String query = "INSERT INTO categories (name, description) VALUES(?,?)";
       try(
               Connection cn = getConnection();
               PreparedStatement ps = cn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
               )
       {
           ps.setString(1, category.getName());
           ps.setString(2, category.getDescription());

           int rowsAffected = ps.executeUpdate();

           if (rowsAffected > 0) {
               ResultSet keys = ps.getGeneratedKeys();
               if (keys.next()) {
                   int newID = keys.getInt(1);
                   category.setCategoryId(newID);
               }
           }
           return category;
       }
       catch (SQLException e){
           System.out.println("ERROR ALERT: " + e.getMessage());
       }
        return null;
    }

    @Override
    public void update(int categoryId, Category category)
    {// update category
      String query = "UPDATE categories SET name = ?, description = ? WHERE category_id = ?";
      try(
              Connection con = getConnection();
              PreparedStatement ps = con.prepareStatement(query)
              )
      {
          ps.setString(1, category.getName());
          ps.setString(2, category.getDescription());
          ps.setInt(3, categoryId);
          ps.executeUpdate();
      }
      catch (SQLException e){
          System.out.println("ERROR ALERT: " + e.getMessage());
      }
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
