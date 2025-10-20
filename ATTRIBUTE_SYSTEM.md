# Product Attribute System

## Overview

The product attribute system allows you to define flexible attributes for different product categories. For example:
- **Cakes** can have attributes like "Vegan", "Gluten-Free", "Size", "Flavor"
- **Cups** can have attributes like "Material", "Capacity", "Personalizable"
- **Catering** can have attributes like "Number of People", "Event Type", "Dietary Options"

## Database Schema

### Tables Created

1. **`product_attributes`** - Defines available attributes for each category
   - `attribute_id` (Primary Key)
   - `name` - Attribute name (e.g., "Vegan", "Size")
   - `data_type` - Type of data ("text", "number", "boolean", "select")
   - `description` - Human-readable description
   - `required` - Whether this attribute is mandatory
   - `select_options` - JSON string with options for "select" type
   - `category_id` - Which category this attribute applies to

2. **`product_attribute_values`** - Stores actual values for products
   - `value_id` (Primary Key)
   - `product_id` - Reference to product
   - `attribute_id` - Reference to attribute definition
   - `value` - The actual value (stored as text)

## API Endpoints

### Attribute Management

#### Get Attributes for Category
```
GET /products/attributes/category/{categoryId}
```
Returns all attributes available for a specific category.

#### Create New Attribute
```
POST /products/attributes
Content-Type: application/json

{
  "name": "Vegan",
  "data_type": "boolean",
  "description": "Indicates if the product is vegan",
  "required": false,
  "select_options": null,
  "category_id": 1
}
```

### Product Attribute Values

#### Get Product Attributes
```
GET /products/{productId}/attributes
```
Returns all attribute values for a specific product.

#### Set Product Attribute Value
```
POST /products/{productId}/attributes/{attributeId}
Content-Type: text/plain

"true"
```

#### Delete Product Attribute Value
```
DELETE /products/{productId}/attributes/{attributeId}
```

## Data Types

### 1. Boolean
- For yes/no attributes (e.g., "Vegan", "Gluten-Free")
- Values: "true" or "false"

### 2. Text
- For free-form text (e.g., "Description", "Special Notes")
- Any string value

### 3. Number
- For numeric values (e.g., "Capacity", "Number of People")
- Stored as text but validated as numbers

### 4. Select
- For predefined options (e.g., "Size", "Material")
- Options defined in `select_options` field as comma-separated values

## Example Usage

### Creating Attributes for Cakes

```json
{
  "name": "Vegan",
  "data_type": "boolean",
  "description": "Indicates if the cake is vegan",
  "required": false,
  "category_id": 1
}
```

```json
{
  "name": "Size",
  "data_type": "select",
  "description": "Cake size",
  "required": true,
  "select_options": ["Small", "Medium", "Large", "Extra Large"],
  "category_id": 1
}
```

### Setting Values for Products

```bash
# Set a cake as vegan
POST /products/1/attributes/2
"true"

# Set cake size
POST /products/1/attributes/4
"Large"
```

## Sample Data

The system comes with pre-configured attributes:

### For Cakes (Category 1)
- **Vegan** (boolean) - Is the cake vegan?
- **Sin TACC** (boolean) - Is the cake gluten-free?
- **Tamaño** (select) - Cake size
- **Sabor** (select) - Cake flavor
- **Decoración** (text) - Decoration description

### For Cups (Category 2)
- **Material** (select) - Cup material
- **Capacidad** (select) - Cup capacity
- **Personalizable** (boolean) - Can be customized?
- **Diseño** (text) - Design description

### For Catering (Category 3)
- **Número de Personas** (number) - Number of people
- **Tipo de Evento** (select) - Event type
- **Incluye Decoración** (boolean) - Includes decoration?
- **Opciones Dietéticas** (select) - Dietary options

## Benefits

1. **Flexibility**: Different categories can have completely different attributes
2. **Extensibility**: Easy to add new attributes without code changes
3. **Type Safety**: Different data types ensure proper validation
4. **User-Friendly**: Select options provide guided input
5. **Searchable**: Attributes can be used for filtering and searching products

## Frontend Integration

The frontend can:
1. Fetch available attributes for a category
2. Display appropriate input controls based on data type
3. Show attribute values in product listings
4. Use attributes for filtering and search
5. Validate required attributes before product creation

## Migration

The system includes a database migration script that:
1. Creates the necessary tables
2. Sets up proper foreign key relationships
3. Creates indexes for performance
4. Includes sample data for testing
