package Clases;

//Primero cargamos las librerías necesarias para correr nuestro programita =)
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.*;

/**
 *
 * @author derketzer
 * @email  der_ketzer@der-ketzer.com
 */

//La clase, que es públic, la nombraremos Tienda, ya que es un programa para una tienda.
public class Tienda extends JFrame{

    JLabel lbl_Nombre, lbl_Fecha, lbl_Calle, lbl_Colonia,
           lbl_CP, lbl_Num, lbl_Estado, lbl_Telefono,
           lbl_Email, lbl_Total, lbl_Pago,
           lbl_TC_Num, lbl_Fecha_Expiracion, lbl_ID, lbl_Subtotal,
           lbl_IVA, lbl_Titulo, lbl_Subtitulo;

    JTextField txt_Nombre, txt_Calle, txt_Colonia,
           txt_CP, txt_Num, txt_Tel_Lada, txt_Tel_Num, txt_Email,
           txt_Total, txt_TC_Num, txt_ID, txt_Subtotal, txt_IVA;

    JRadioButton rad_Cheque, rad_TC;

    ButtonGroup grp_Pago;

    JButton btn_Guardar, btn_Limpiar, btn_Salir;

    String[] Lista_Meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo",
                       "Junio", "Julio", "Agosto", "Septiembre",
                       "Octubre", "Noviembre", "Diciembre"
                     };

    String[] Lista_Colores = {"Amarillo", "Azul", "Beige", "Blanco", "Morado", "Naranja", "Negro", "Rojo", "Rosa", "Violeta"};

    String[] Lista_Tamanhos = {"Chico", "Mediano", "Grande", "Extragrande"};

    GridBagLayout Layout = new GridBagLayout();
    Container Pantalla = getContentPane();
    GridBagConstraints GBC = new GridBagConstraints();

    ArrayList Anhos = new ArrayList();
    ArrayList Dias = new ArrayList();
    Object[] Lista_Anhos;
    Object[] Lista_Dias;
    JComboBox com_TC_Anho, com_TC_Mes, com_TC_Dia, com_Anho, com_Mese, com_Dia, com_Colores, com_Tamanhos;

    JTable Tabla;
    TableModel Modelo;

    Object Datos[][];
    JScrollPane Scroll;
    String Encabezados[] = {"ID Producto", "Descripcion", "Cantidad", "Tamaño", "Color", "Precio Unitario", "Subtotal"};

    TableColumn tc_IDProducto;
    TableColumn tc_Descripcion;
    TableColumn tc_Cantidad;
    TableColumn tc_Tamanho;
    TableColumn tc_Color;
    TableColumn tc_Precio;
    TableColumn tc_Subtotal;

    int Max_Rows, Max_Cols;

    Double Gran_Subtotal, IVA, Total;

    public Tienda(){

        Max_Rows = 20;
        Max_Cols = 7;

        //Esto crea el combobox de los años para popularlo
        //Crea el arreglo con los años
        for(int temp = 2009; temp <= 2009+15; temp++){
            Anhos.add(temp);
        }
        //Crea el arreglo con los dias
        for(int temp = 1; temp <= 31; temp++){
            Dias.add(temp);
        }

        lbl_ID                  = new JLabel("ID");
        lbl_Nombre              = new JLabel("Nombre: ");
        lbl_Fecha               = new JLabel("Fecha: ");
        lbl_Calle               = new JLabel("Calle: ");
        lbl_Colonia             = new JLabel("Colonia: ");
        lbl_CP                  = new JLabel("C.P. ");
        lbl_Num                 = new JLabel("Número: ");
        lbl_Estado              = new JLabel("Estado: ");
        lbl_Telefono            = new JLabel("Teléfono: ");
        lbl_Email               = new JLabel("E-Mail: ");
        lbl_Pago                = new JLabel("Pago: ");
        lbl_TC_Num              = new JLabel("Número de rarjeta: ");
        lbl_Fecha_Expiracion    = new JLabel("Fecha de expiración");
        lbl_Subtotal            = new JLabel("Subtotal");
        lbl_IVA                 = new JLabel("IVA");
        lbl_Total               = new JLabel("Total");
        lbl_Titulo              = new JLabel("DirectClothing, Inc.");
        lbl_Subtitulo           = new JLabel("\"The Shirt Company\"");

        txt_Nombre              = new JTextField(50);
        txt_Calle               = new JTextField();
        txt_Colonia             = new JTextField();
        txt_CP                  = new JTextField();
        txt_Num                 = new JTextField();
        txt_Tel_Lada            = new JTextField();
        txt_Tel_Num             = new JTextField();
        txt_Email               = new JTextField();
        txt_TC_Num              = new JTextField();
        
        txt_ID                  = new JTextField();
        txt_ID.setEditable(false);

        txt_Subtotal            = new JTextField();
        txt_Subtotal.setEditable(false);

        txt_IVA                 = new JTextField();
        txt_IVA.setEditable(false);

        txt_Total               = new JTextField();
        txt_Total.setEditable(false);

        rad_Cheque              = new JRadioButton("Cheque", true);
        rad_TC                  = new JRadioButton("Tarjeta de crédito", false);

        grp_Pago                = new ButtonGroup();

        grp_Pago.add(rad_Cheque);
        grp_Pago.add(rad_TC);

        btn_Guardar             = new JButton("Guardar");
        btn_Limpiar             = new JButton("Limpiar");
        btn_Salir               = new JButton("Salir");

        setTitle("DirectClothing, Inc. - \"The Shirt Company\"");

        Pantalla.setLayout(Layout);

        //Convierte el arreglo de años a algo entendible para el combobox
        Object[] Lista_Anhos = Anhos.toArray();
        //Convierte el arreglo de dias a algo entendible para el combobox
        Object[] Lista_Dias = Dias.toArray();
        
        //Crea el combobox de los meses para la TC
        JComboBox com_TC_Dia = new JComboBox(Lista_Dias);
        //Crea el combobox de los meses para la TC
        JComboBox com_TC_Mes = new JComboBox(Lista_Meses);
        //Crea el combobox de los años para la TC
        JComboBox com_TC_Anho = new JComboBox(Lista_Anhos);

        //Crea el combobox de los meses para la fecha
        JComboBox com_Dia = new JComboBox(Lista_Dias);
        //Crea el combobox de los meses para la fecha
        JComboBox com_Mes = new JComboBox(Lista_Meses);
        //Crea el combobox de los años para la fecha
        JComboBox com_Anho = new JComboBox(Lista_Anhos);

        JComboBox com_Colores = new JComboBox(Lista_Colores);

        JComboBox com_Tamanhos = new JComboBox(Lista_Tamanhos);

        Datos = new Object[Max_Cols][Max_Rows];

        for(int i=0; i<Max_Cols; i++){
            for(int j=0; j<Max_Rows; j++){
                Datos[i][j] = "";
            }
        }

        Modelo = new AbstractTableModel(){
            public int getColumnCount(){
                return Encabezados.length;
            }
            public int getRowCount(){
                return Datos.length;
            }
            public Object getValueAt(int row, int col){
                return Datos[row][col];
            }
            public String getColumnName(int c){
                return Encabezados[c];
            }
            public boolean isCellEditable(int row, int col){
                if(col == 1 || col == 5 || col == 6){
                    return false;
                }
                else{
                    return true;
                }
            }
            public void setValueAt(Object objeto, int row, int col){
                if(col == 0){
                    if(objeto.toString().length() >= 5){
                        Datos[row][1] = "Hello World!";
                        Datos[row][5] = "10";

                        Tabla.repaint();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "ID inválido, tiene que ser al menos de 5 dígitos.");
                    }
                }
                
                if(col == 2){
                    String val1 = Datos[row][5].toString();
                    String val2 = objeto.toString();

                    Double Subtotal = Double.valueOf(val1) * Double.valueOf(val2);

                    Datos[row][6] = Subtotal.toString();
                    
                    Gran_Subtotal = 0.0;

                    for(int i=0; i<Datos.length; i++){
                        String val3 = Datos[i][6].toString();

                        if(val3 != ""){
                            Gran_Subtotal += Double.valueOf(val3);
                        }
                    }

                    IVA = Gran_Subtotal*0.15;
                    Total = Gran_Subtotal + IVA;

                    txt_Subtotal.setText(Gran_Subtotal.toString());
                    txt_IVA.setText(IVA.toString());
                    txt_Total.setText(Total.toString());

                    Tabla.repaint();
                }
                
                Datos[row][col] = objeto;
            }
            public Class getColumnClass(int c){
                return getValueAt(0,c).getClass();
            }
        };

        Tabla = new JTable(Modelo);
        Scroll = new JScrollPane(Tabla);

        tc_Tamanho   = Tabla.getColumn("Tamaño");
        tc_Color    = Tabla.getColumn("Color");

        tc_Tamanho.setCellEditor(new DefaultCellEditor(com_Tamanhos));
        tc_Color.setCellEditor(new DefaultCellEditor(com_Colores));

        buildConstraints(lbl_Titulo     , 0, 0, 6, 1, GridBagConstraints.NONE);

        buildConstraints(lbl_Subtitulo  , 0, 1, 6, 1, GridBagConstraints.NONE);

        buildConstraints(lbl_ID         , 3, 2, 1, 1, GridBagConstraints.NONE);
        buildConstraints(txt_ID         , 4, 2, 2, 1, GridBagConstraints.HORIZONTAL);

        buildConstraints(lbl_Nombre     , 0, 3, 1, 1, GridBagConstraints.NONE);
        buildConstraints(txt_Nombre     , 1, 3, 1, 1, GridBagConstraints.HORIZONTAL);
        buildConstraints(lbl_Fecha      , 2, 3, 1, 1, GridBagConstraints.NONE);
        buildConstraints(com_Dia        , 3, 3, 1, 1, GridBagConstraints.NONE);
        buildConstraints(com_Mes        , 4, 3, 1, 1, GridBagConstraints.NONE);
        buildConstraints(com_Anho       , 5, 3, 1, 1, GridBagConstraints.NONE);

        buildConstraints(lbl_Calle      , 0, 4, 1, 1, GridBagConstraints.NONE);
        buildConstraints(txt_Calle      , 1, 4, 2, 1, GridBagConstraints.HORIZONTAL);
        buildConstraints(lbl_Num        , 3, 4, 1, 1, GridBagConstraints.NONE);
        buildConstraints(txt_Num        , 4, 4, 2, 1, GridBagConstraints.HORIZONTAL);

        buildConstraints(lbl_Colonia    , 0, 5, 1, 1, GridBagConstraints.NONE);
        buildConstraints(txt_Colonia    , 1, 5, 2, 1, GridBagConstraints.HORIZONTAL);
        buildConstraints(lbl_CP         , 3, 5, 1, 1, GridBagConstraints.NONE);
        buildConstraints(txt_CP         , 4, 5, 2, 1, GridBagConstraints.HORIZONTAL);

        buildConstraints(lbl_Telefono   , 0, 6, 1, 1, GridBagConstraints.NONE);
        buildConstraints(txt_Tel_Lada   , 1, 6, 1, 1, GridBagConstraints.HORIZONTAL);
        buildConstraints(txt_Tel_Num    , 2, 6, 1, 1, GridBagConstraints.HORIZONTAL);
        buildConstraints(lbl_Email      , 3, 6, 1, 1, GridBagConstraints.NONE);
        buildConstraints(txt_Email      , 4, 6, 2, 1, GridBagConstraints.HORIZONTAL);

        buildConstraints(Scroll         , 0, 7, 6, 1, GridBagConstraints.BOTH);

        buildConstraints(lbl_Subtotal   , 4, 8, 1, 1, GridBagConstraints.NONE);
        buildConstraints(txt_Subtotal   , 5, 8, 1, 1, GridBagConstraints.HORIZONTAL);

        buildConstraints(lbl_IVA        , 4, 9, 1, 1, GridBagConstraints.NONE);
        buildConstraints(txt_IVA        , 5, 9, 1, 1, GridBagConstraints.HORIZONTAL);

        buildConstraints(lbl_Total      , 4, 10, 1, 1, GridBagConstraints.NONE);
        buildConstraints(txt_Total      , 5, 10, 1, 1, GridBagConstraints.HORIZONTAL);

        buildConstraints(lbl_Pago       , 0, 11, 1, 1, GridBagConstraints.NONE);
        buildConstraints(rad_Cheque     , 1, 11, 1, 1, GridBagConstraints.HORIZONTAL);
        buildConstraints(rad_TC         , 2, 11, 1, 1, GridBagConstraints.NONE);

        buildConstraints(lbl_TC_Num     , 0, 12, 1, 1, GridBagConstraints.NONE);
        buildConstraints(txt_TC_Num     , 1, 12, 1, 1, GridBagConstraints.HORIZONTAL);

        buildConstraints(lbl_Fecha_Expiracion   , 0, 13, 1, 1, GridBagConstraints.NONE);
        buildConstraints(com_TC_Dia             , 1, 13, 1, 1, GridBagConstraints.NONE);
        buildConstraints(com_TC_Mes             , 2, 13, 1, 1, GridBagConstraints.NONE);
        buildConstraints(com_TC_Anho            , 3, 13, 1, 1, GridBagConstraints.NONE);

        //lbl_TC_Num.setVisible(false);
        //txt_TC_Num.setVisible(false);
        //lbl_Fecha_Expiracion.setVisible(false);
        //com_TC_Dia.setVisible(false);
        //com_TC_Mes.setVisible(false);
        //com_TC_Anho.setVisible(false);

        buildConstraints(btn_Guardar    , 1, 14, 1, 1, GridBagConstraints.NONE);
        buildConstraints(btn_Limpiar    , 2, 14, 1, 1, GridBagConstraints.NONE);
        buildConstraints(btn_Salir      , 3, 14, 1, 1, GridBagConstraints.NONE);

        setSize(800,600);
        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btn_Guardar.addActionListener(new Eventos());
        btn_Limpiar.addActionListener(new Eventos());
        btn_Salir.addActionListener(new Eventos());
        //rad_TC.addActionListener(new Eventos());
        //rad_Cheque.addActionListener(new Eventos());

    }

    private void buildConstraints(Component componente, int x, int y, int w, int h, int fill){
        GBC.gridx = x;
        GBC.gridy = y;
        GBC.gridwidth = w;
        GBC.gridheight = h;
        GBC.weightx = 1;
        GBC.weighty = 1;
        GBC.fill = fill;
        GBC.anchor = GridBagConstraints.CENTER;

        Layout.setConstraints(componente, GBC);
        Pantalla.add(componente);
    }

    public class Eventos implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == btn_Guardar){
                if(txt_ID.getText().matches("^\\s*$")){
                    JOptionPane.showMessageDialog(null, "Falta ID");
                }
                else if(!txt_ID.getText().matches("([0-9]*)")){
                    JOptionPane.showMessageDialog(null, "El ID sólo puede ser numérico");
                }
                else if(!txt_Email.getText().matches(".+@.+\\.[a-z]+")){
                    JOptionPane.showMessageDialog(null, "El E-Mail es inválido");
                }
                else{
                }
            }
            else if(e.getSource() == btn_Limpiar){
                
            }
            else if(e.getSource() == btn_Salir){
                System.exit(0);
            }
            else if(e.getSource() == rad_TC){
                lbl_TC_Num.setVisible(true);
                txt_TC_Num.setVisible(true);
                lbl_Fecha_Expiracion.setVisible(true);
                //com_TC_Dia.setVisible(true);
                //com_TC_Mes.setVisible(true);
                //com_TC_Anho.setVisible(true);
            }
            else if(e.getSource() == rad_Cheque){
                lbl_TC_Num.setVisible(false);
                txt_TC_Num.setVisible(false);
                lbl_Fecha_Expiracion.setVisible(false);
                com_TC_Dia.setVisible(false);
                com_TC_Mes.setVisible(false);
                com_TC_Anho.setVisible(false);
            }
        }
    }
}
