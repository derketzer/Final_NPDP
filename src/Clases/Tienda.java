package Clases;

//Primero cargamos las librerías necesarias para correr nuestro programita =)
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.Calendar;

/**
 *
 * @author Der Ketzer
 * @email  der_ketzer@der-ketzer.com
 */

//La clase la nombraremos Tienda, ya que es un programa para una tienda.
public class Tienda extends JFrame{

    JLabel lbl_Nombre, lbl_Calle, lbl_Colonia, lbl_ID_Cliente,
           lbl_CP, lbl_Num, lbl_Estado, lbl_Telefono,
           lbl_Email, lbl_Total, lbl_Pago,
           lbl_TC_Num, lbl_Fecha_Expiracion, lbl_ID, lbl_Subtotal,
           lbl_IVA, lbl_Titulo, lbl_Subtitulo;

    JTextField txt_Nombre, txt_Calle, txt_Colonia, txt_ID_Cliente,
               txt_CP, txt_Num, txt_Tel_Lada, txt_Tel_Num, txt_Email,
               txt_Total, txt_TC_Num, txt_ID, txt_Subtotal, txt_IVA;

    JRadioButton rad_Cheque, rad_TC;

    ButtonGroup grp_Pago;

    JButton btn_Guardar, btn_Limpiar, btn_Salir;

    String[] Lista_Meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo",
                             "Junio", "Julio", "Agosto", "Septiembre",
                             "Octubre", "Noviembre", "Diciembre"
                           };

    String[] Lista_Colores = {"Amarillo", "Azul", "Beige", "Blanco", "Morado",
                              "Naranja", "Negro", "Rojo", "Rosa", "Violeta"
                             };

    String[] Lista_Tamanhos = {"Chico", "Mediano", "Grande", "Extragrande"};

    GridBagLayout Layout = new GridBagLayout();
    Container Pantalla = getContentPane();
    GridBagConstraints GBC = new GridBagConstraints();

    Vector<String> Anhos, Dias;
    DefaultComboBoxModel com_mod_Anhos, com_mod_Dias;
    Object[] Lista_Dias;
    JComboBox com_TC_Anho, com_TC_Mes, com_TC_Dia, com_Colores, com_Tamanhos;

    JTable      Tabla;
    TableModel  Modelo;

    Object Datos[][];
    JScrollPane Scroll;
    String Encabezados[] = {"ID Producto", "Descripcion", "Cantidad", "Tamaño",
                            "Color", "Precio Unitario", "Subtotal"
                           };

    TableColumn tc_IDProducto;
    TableColumn tc_Descripcion;
    TableColumn tc_Cantidad;
    TableColumn tc_Tamanho;
    TableColumn tc_Color;
    TableColumn tc_Precio;
    TableColumn tc_Subtotal;

    int Max_Rows, Max_Cols;
    boolean Flag_Existe;

    Double Gran_Subtotal, IVA, Total;

    Connection  Mysqli;
    Statement   State, Select;

    Calendar Calendario = Calendar.getInstance();

    String DBhost = "localhost";
    String DBname = "Final_NPDP";
    String DBuser = "npdp";
    String DBpass = "V_@E)dY<b!pvx.|&";
    String DBtipo = "mysql";

    public Tienda(){

        Max_Rows    = 20;
        Max_Cols    = 7;
        Flag_Existe = false;

        int Anho_Inicial = Calendario.get(Calendar.YEAR);

        //Esto crea el combobox de los años para popularlo
        //Crea el arreglo con los años
        Vector<String> Anhos = new Vector<String>();
        Vector<String> Dias = new Vector<String>();

        for(int temp = Anho_Inicial; temp <= Anho_Inicial+15; temp++){
            Anhos.add(temp + "");
        }

        //Crea el arreglo con los dias
        for(int temp = 1; temp <= 31; temp++){
            Dias.add(temp + "");
        }

        lbl_ID                  = new JLabel("ID");
        lbl_ID_Cliente          = new JLabel("ID Cliente");
        lbl_Nombre              = new JLabel("Nombre: ");
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

        txt_Nombre              = new JTextField();//84
        txt_Calle               = new JTextField();//84
        txt_Colonia             = new JTextField();//84
        txt_CP                  = new JTextField();//5
        txt_Num                 = new JTextField();//5
        txt_Tel_Lada            = new JTextField();//4
        txt_Tel_Num             = new JTextField();//20
        txt_Email               = new JTextField();//
        txt_TC_Num              = new JTextField();//20
        txt_ID_Cliente          = new JTextField();//11
        
        txt_ID                  = new JTextField();//11
        txt_ID.setEditable(false);

        txt_Subtotal            = new JTextField();//10
        txt_Subtotal.setEditable(false);

        txt_IVA                 = new JTextField();//10
        txt_IVA.setEditable(false);

        txt_Total               = new JTextField();//10
        txt_Total.setEditable(false);

        rad_Cheque              = new JRadioButton("Cheque", true);
        rad_TC                  = new JRadioButton("Tarjeta de crédito", false);

        grp_Pago                = new ButtonGroup();

        grp_Pago.add(rad_Cheque);
        grp_Pago.add(rad_TC);

        btn_Guardar             = new JButton("Guardar");
        btn_Limpiar             = new JButton("Limpiar");
        btn_Salir               = new JButton("Salir");

        //Convierte el arreglo de años a algo entendible para el combobox
        com_mod_Anhos = new DefaultComboBoxModel(Anhos);
        //Convierte el arreglo de dias a algo entendible para el combobox
        com_mod_Dias = new DefaultComboBoxModel(Dias);
        
        //Crea el combobox de los meses para la TC
        com_TC_Dia = new JComboBox();
        com_TC_Anho = new JComboBox();

        com_TC_Dia.setModel(com_mod_Dias);
        //Crea el combobox de los meses para la TC
        com_TC_Mes = new JComboBox(Lista_Meses);
        //Crea el combobox de los años para la TC
        com_TC_Anho.setModel(com_mod_Anhos);

        com_Colores = new JComboBox(Lista_Colores);

        com_Tamanhos = new JComboBox(Lista_Tamanhos);

        Conecta();

        setTitle("DirectClothing, Inc. - \"The Shirt Company\"");

        Pantalla.setLayout(Layout);

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
                    try{
                        Select.execute("SELECT Descripcion, Precio FROM"
                                       + " Productos WHERE ID='"
                                       + objeto.toString() + "'");
                        
                        ResultSet RS = Select.getResultSet();
                        
                        if(RS.next()){

                            Datos[row][1] = RS.getString("Descripcion");
                            Datos[row][5] = RS.getString("Precio");
                        }
                        else{
                            Alerta("El producto no existe!");
                        }
                    }
                    catch(Exception e){
                        Alerta(e.getMessage());
                    }

                    Tabla.repaint();
                }
                
                if(col == 2 && Datos[row][5] != ""){
                    String val1 = Datos[row][5].toString();
                    String val2 = objeto.toString();

                    try{
                        Select.execute("SELECT * FROM Almacen WHERE ID_Producto='" + Datos[row][0].toString() + "'");
                        ResultSet RS = Select.getResultSet();

                        if(RS.next()){
                            int Existencias = Integer.parseInt(RS.getString("Cantidad"));

                            if(Existencias >= Integer.parseInt(val2)){

                                Double Subtotal = Double.valueOf(val1) * Double.valueOf(val2);

                                Datos[row][6] = Subtotal.toString();

                                Gran_Subtotal = 0.0;

                                for(int i=0; i<Datos.length; i++){
                                    String val3 = Datos[i][6].toString();
                                    if(!val3.equals("")){
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
                            else{
                                Alerta("No hay suficientes existencias en almacén.");
                            }
                        }
                        else{
                            Alerta("No hay suficientes existencias en almacén.");
                        }
                    }
                    catch(Exception e){}
                }
                
                Datos[row][col] = objeto;
            }
            public Class getColumnClass(int c){
                return getValueAt(0,c).getClass();
            }
        };

        Tabla       = new JTable(Modelo);
        Scroll      = new JScrollPane(Tabla);

        tc_Tamanho  = Tabla.getColumn("Tamaño");
        tc_Color    = Tabla.getColumn("Color");

        tc_Tamanho.setCellEditor(new DefaultCellEditor(com_Tamanhos));
        tc_Color.setCellEditor(new DefaultCellEditor(com_Colores));

        buildConstraints(lbl_Titulo     , 0, 0, 6, 1, GridBagConstraints.NONE);

        buildConstraints(lbl_Subtitulo  , 0, 1, 6, 1, GridBagConstraints.NONE);

        buildConstraints(lbl_ID_Cliente , 0, 2, 1, 1, GridBagConstraints.NONE);
        buildConstraints(txt_ID_Cliente , 1, 2, 1, 1, GridBagConstraints.HORIZONTAL);
        buildConstraints(lbl_ID         , 3, 2, 1, 1, GridBagConstraints.NONE);
        buildConstraints(txt_ID         , 4, 2, 2, 1, GridBagConstraints.HORIZONTAL);

        buildConstraints(lbl_Nombre     , 0, 3, 1, 1, GridBagConstraints.NONE);
        buildConstraints(txt_Nombre     , 1, 3, 2, 1, GridBagConstraints.HORIZONTAL);

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

        lbl_TC_Num.setVisible(false);
        txt_TC_Num.setVisible(false);
        lbl_Fecha_Expiracion.setVisible(false);
        com_TC_Dia.setVisible(false);
        com_TC_Mes.setVisible(false);
        com_TC_Anho.setVisible(false);

        buildConstraints(btn_Guardar    , 1, 14, 1, 1, GridBagConstraints.NONE);
        buildConstraints(btn_Limpiar    , 2, 14, 1, 1, GridBagConstraints.NONE);
        buildConstraints(btn_Salir      , 3, 14, 1, 1, GridBagConstraints.NONE);

        setSize(800,600);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
		Desconecta();
                System.exit(0);
            }
            public void windowOpened(WindowEvent e) {
                setExtendedState(Frame.MAXIMIZED_BOTH);
            }

        });

        btn_Guardar.addActionListener(new Eventos());
        btn_Limpiar.addActionListener(new Eventos());
        btn_Salir.addActionListener(new Eventos());
        rad_TC.addActionListener(new Eventos());
        rad_Cheque.addActionListener(new Eventos());

        txt_ID_Cliente.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try{
                        Select.execute("SELECT * FROM Clientes WHERE ID='" + txt_ID_Cliente.getText() + "'");
                        ResultSet RS = Select.getResultSet();
                        if(RS.next()){
                            txt_Nombre.setText(RS.getString("Nombre"));
                            txt_Calle.setText(RS.getString("Calle"));
                            txt_Num.setText(RS.getString("Numero"));
                            txt_Colonia.setText(RS.getString("Colonia"));
                            txt_CP.setText(RS.getString("CP"));
                            txt_Tel_Lada.setText(RS.getString("Lada"));
                            txt_Tel_Num.setText(RS.getString("Telefono"));
                            txt_Email.setText(RS.getString("Email"));
                        }
                        else{
                            Limpiame();
                        }
                    }
                    catch(Exception p){
                        Alerta(p.getMessage());
                    }
                }
            }
        });

        txt_ID_Cliente.addFocusListener(new FocusListener(){
            public void focusGained(FocusEvent e) {
            }
            public void focusLost(FocusEvent e) {
                    try{
                        Select.execute("SELECT * FROM Clientes WHERE ID='" + txt_ID_Cliente.getText() + "'");
                        ResultSet RS = Select.getResultSet();
                        if(RS.next()){
                            txt_Nombre.setText(RS.getString("Nombre"));
                            txt_Calle.setText(RS.getString("Calle"));
                            txt_Num.setText(RS.getString("Numero"));
                            txt_Colonia.setText(RS.getString("Colonia"));
                            txt_CP.setText(RS.getString("CP"));
                            txt_Tel_Lada.setText(RS.getString("Lada"));
                            txt_Tel_Num.setText(RS.getString("Telefono"));
                            txt_Email.setText(RS.getString("Email"));
                            Flag_Existe = true;
                        }
                        else{
                            Limpiame();
                        }
                    }
                    catch(Exception p){
                        Alerta(p.getMessage());
                    }
            }
        });
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
                if(txt_Nombre.getText().matches("^\\s*$")){
                    Alerta("Falta el \"Nombre\"");
                }
                else if(txt_Calle.getText().matches("^\\s*$")){
                    Alerta("Falta la \"Calle\"");
                }
                else if(txt_Colonia.getText().matches("^\\s*$")){
                    Alerta("Falta la \"Colonia\"");
                }
                else if(txt_Tel_Lada.getText().matches("^\\s*$")){
                    Alerta("Falta la \"Lada\"");
                }
                else if(!txt_Tel_Lada.getText().matches("([0-9]*)")){
                    Alerta("La \"Lada\" sólo puede ser numérica");
                }
                else if(txt_Tel_Num.getText().matches("^\\s*$")){
                    Alerta("Falta el \"Número telefónico\"");
                }
                else if(!txt_Tel_Num.getText().matches("([0-9]*)")){
                    Alerta("El \"Número telefónico\" sólo puede ser numérico");
                }
                else if(txt_Num.getText().matches("^\\s*$")){
                    Alerta("Falta el \"Número\"");
                }
                else if(!txt_Num.getText().matches("([0-9]*)")){
                    Alerta("El \"Número\" sólo puede ser numérico");
                }
                else if(txt_CP.getText().matches("^\\s*$")){
                    Alerta("Falta el \"CP\"");
                }
                else if(!txt_CP.getText().matches("([0-9]*)")){
                    Alerta("El \"CP\" sólo puede ser numérico");
                }
                else if(txt_Email.getText().matches("^\\s*$")){
                    Alerta("Falta el \"Email\"");
                }
                else if(!txt_Email.getText().matches(".+@.+\\.[a-z]+")){
                    Alerta("El \"E-Mail\" es inválido");
                }
                else if(rad_TC.isSelected() && txt_TC_Num.getText().matches("^\\s*$")){
                        Alerta("Falta la \"Tarjeta de crédito\"");
                }
                else if(rad_TC.isSelected() && !txt_TC_Num.getText().matches("([0-9]*)")){
                        Alerta("La \"Tarjeta de crédito\" sólo pueden ser números.");
                }
                else{
                    boolean Falta_Datos = false, Error = false;

                    for(int i=0; i<Datos.length; i++){
                            if(Datos[i][0] != "" && !(Datos[i][2] != "" && Datos[i][3] != "" && Datos[i][4] != "")){
                                if(Confirma("Faltan datos en la partida numero " + (i+1)) == 0){
                                    Falta_Datos = true;
                                }
                            }
                    }

                    if(!Falta_Datos){
                        //Si no existe el cliente lo guarda
                        try{
                            if(Flag_Existe){
                                //Upatea el cliente
                                Select.execute("UPDATE Clientes SET "
                                               + "Nombre='" + txt_Nombre.getText() + "',"
                                               + "Calle='" + txt_Calle.getText() + "',"
                                               + "Numero='" + txt_Num.getText() + "',"
                                               + "Colonia='" + txt_Colonia.getText() + "',"
                                               + "CP='" + txt_CP.getText() + "',"
                                               + "Lada='" + txt_Tel_Lada.getText() + "',"
                                               + "Telefono='" + txt_Tel_Num.getText() + "',"
                                               + "Email='" + txt_Email.getText() + "'");
                            }
                            else{
                                //Guarda el cliente
                                Select.execute("INSERT INTO Clientes VALUES(NULL, '" + txt_Nombre.getText() + "',"
                                               + "'" + txt_Calle.getText() + "', '" + txt_Num.getText() + "',"
                                               + "'" + txt_Colonia.getText() + "', '" + txt_CP.getText() + "',"
                                               + "'" + txt_Tel_Lada.getText() + "', '" + txt_Tel_Num.getText() + "',"
                                               + "'" + txt_Email.getText() + "')");
                            }
                        }
                        catch(Exception p){
                            Alerta("No se pudo guardar el \"Cliente\" porque " + p.getMessage());
                            Error = true;
                        }

                        if(!Error){
                            String TC_Num, TC_Exp;

                            TC_Num = "0";
                            TC_Exp = "0000-00-00";

                            if(rad_TC.isSelected()){
                                TC_Num = txt_TC_Num.getText();
                                TC_Exp = com_TC_Anho.getSelectedItem().toString() +"-"+(com_TC_Mes.getSelectedIndex()+1)+"-"+com_TC_Dia.getSelectedItem().toString();
                            }

                            try{
                                Select.execute("INSERT INTO Pedidos VALUES(NULL, '" + txt_ID_Cliente.getText() + "',"
                                                   + "NOW(), '" + txt_Subtotal.getText() + "',"
                                                   + "'" + txt_IVA.getText() + "', '" + txt_Total.getText() + "',"
                                                   + "'" + TC_Num + "', '" + TC_Exp + "')");
                            }
                            catch(Exception p){
                                Alerta("No se pudo guardar el \"Pedido\" porque " + p.getMessage());
                                Error = true;
                            }
                        }

                        if(!Error){
                            try{
                                for(int i=0; i<Datos.length; i++){
                                    if(Datos[i][0] != "" && Datos[i][2] != "" && Datos[i][3] != "" && Datos[i][4] != ""){
                                        Select.execute("INSERT INTO Partidas VALUES(NULL, '" + txt_ID.getText() + "',"
                                                       + "'" + Datos[i][0].toString() + "', '" + Datos[i][3].toString() + "',"
                                                       + "'" + Datos[i][4].toString() + "', '" + Datos[i][2].toString() + "')");

                                        Select.execute("UPDATE Almacen SET Cantidad = Cantidad - " + Datos[i][2].toString()
                                                       + " WHERE ID_Producto = '" + Datos[i][0].toString() + "'");
                                    }
                                }
                            }
                            catch(Exception p){
                                Alerta("No se pudieron guardar las \"Partidas\" porque " + p.getMessage());
                                Error = true;
                            }
                        }

                        if(!Error){
                            Limpiame();

                            int Antiguo_ID = Integer.parseInt(txt_ID.getText()) + 1;
                            txt_ID.setText(Antiguo_ID + "");
                        }
                    }
                }
            }
            else if(e.getSource() == btn_Limpiar){
                Limpiame();
            }
            else if(e.getSource() == btn_Salir){
                Desconecta();
                System.exit(0);
            }
            else if(e.getSource() == rad_TC){
                lbl_TC_Num.setVisible(true);
                txt_TC_Num.setVisible(true);
                lbl_Fecha_Expiracion.setVisible(true);
                com_TC_Dia.setVisible(true);
                com_TC_Mes.setVisible(true);
                com_TC_Anho.setVisible(true);
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

    void Alerta(String e){
        JOptionPane.showMessageDialog(null,"Error: " + e);
    }

    int Confirma(String e){
        int Respuesta = JOptionPane.showConfirmDialog(null, e, "", JOptionPane.YES_NO_OPTION);

        return Respuesta;
    }

    void Conecta(){
        String val = "";

        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Mysqli = DriverManager.getConnection("jdbc:" + DBtipo + "://" + DBhost
                                                 + "/" + DBname + "", DBuser, DBpass);
            Select = Mysqli.createStatement();
        }catch(Exception e){
            Alerta(e.getMessage());
        }

        try{
            Select.execute("CREATE TABLE IF NOT EXISTS Productos (ID int(11) NOT NULL auto_increment,"
                           + "Descripcion longtext NOT NULL, Precio_Unitario decimal(10,2) NOT NULL,"
                           + "PRIMARY KEY  (ID))");
        }
        catch(Exception e){
            Alerta("No se pudo crear la tabla \"Productos\" porque " + e.getMessage());
        }

        try{
            Select.execute("CREATE TABLE IF NOT EXISTS Almacen (ID_Producto int(11) NOT NULL,"
                           + "Cantidad int(11) NOT NULL, PRIMARY KEY (ID_Producto), UNIQUE KEY"
                           + " Almacen_Prod_ID (ID_Producto))");
        }
        catch(Exception e){
            Alerta("No se pudo crear la tabla \"Almacen\" porque " + e.getMessage());
        }

        try{
            Select.execute("CREATE TABLE IF NOT EXISTS Clientes (ID int(11) NOT NULL auto_increment,"
                           + "Nombre varchar(255) NOT NULL, Calle varchar(100)NOT NULL, Num int(5) NOT"
                           + " NULL, Colonia varchar(100) NOT NULL, CP int(5) NOT NULL, Telefono "
                           + "varchar(20) NOT NULL, Email varchar(150) NOT NULL,"
                           + "PRIMARY KEY (ID))");
        }
        catch(Exception e){
            Alerta("No se pudo crear la tabla \"Clientes\" porque " + e.getMessage());
        }

        try{
            Select.execute("CREATE TABLE IF NOT EXISTS Pedidos (ID int(11) NOT NULL auto_increment,"
                           + "Cliente_ID int(11) NOT NULL, Fecha datetime NOT NULL, Subtotal decimal(10,2)"
                           + "NOT NULL, IVA decimal(10,2) NOT NULL, Total decimal(10,2) NOT NULL,"
                           + "PRIMARY KEY (ID))");
        }
        catch(Exception e){
            Alerta("No se pudo crear la tabla \"Pedidos\" porque " + e.getMessage());
        }

        try{
            Select.execute("CREATE TABLE IF NOT EXISTS Partidas (ID int(11) NOT NULL auto_increment,"
                           + "Pedido_ID int(11) NOT NULL, Producto_ID int(11) NOT NULL, Cantidad int(11)"
                           + "NOT NULL, PRIMARY KEY (ID))");
        }
        catch(Exception e){
            Alerta("No se pudo crear la tabla \"Partidas\" porque " + e.getMessage());
        }

        try{
            Select.execute("SHOW TABLE STATUS LIKE 'Pedidos'");
            ResultSet RS = Select.getResultSet();
            RS.next();
            txt_ID.setText(RS.getString("Auto_increment"));
        }
        catch(Exception e){
        }
    }

    void Desconecta(){
        try{
            Select.close();
            Mysqli.close();
        }catch(Exception e){
            Alerta(e.getMessage());
        }
    }

    void Limpiame(){
        txt_Nombre.setText("");
        txt_Calle.setText("");
        txt_Colonia.setText("");
        txt_CP.setText("");
        txt_Num.setText("");
        txt_Tel_Lada.setText("");
        txt_Tel_Num.setText("");
        txt_Email.setText("");
        txt_TC_Num.setText("");
        txt_Subtotal.setText("");
        txt_IVA.setText("");
        txt_Total.setText("");

        for(int i=0; i<Datos.length; i++){
            Datos[i][0] = "";
            Datos[i][1] = "";
            Datos[i][2] = "";
            Datos[i][3] = "";
            Datos[i][4] = "";
            Datos[i][5] = "";
        }

        Tabla.repaint();
    }

}
