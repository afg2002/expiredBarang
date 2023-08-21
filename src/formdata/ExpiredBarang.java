/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package formdata;

import helper.database;
import helper.reset;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

class ExpiredRowRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Ambil nilai dari kolom "tanggal_expired"
        String tanggalExpiredStr = table.getValueAt(row, 3).toString();

        // Konversi tanggal_expired ke java.sql.Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date tanggalExpiredUtil = dateFormat.parse(tanggalExpiredStr);
            java.sql.Date tanggalExpired = new java.sql.Date(tanggalExpiredUtil.getTime());

            // Pengecekan apakah tanggal_expired sudah lewat
            java.util.Date currentDate = new java.util.Date();
            java.util.Date threeDaysLater = new java.util.Date(currentDate.getTime() + 3 * 24 * 60 * 60 * 1000); // 3 days in milliseconds
            java.util.Date sevenDaysLater = new java.util.Date(currentDate.getTime() + 7 * 24 * 60 * 60 * 1000); // 7 days in milliseconds

            if (tanggalExpired.before(currentDate)) {
                cellComponent.setBackground(Color.RED);
                cellComponent.setForeground(Color.WHITE); // Untuk kontras teks pada latar merah
            } else if (tanggalExpired.before(threeDaysLater)) {
                // Within 3 days from expiration
                cellComponent.setBackground(Color.PINK);
                cellComponent.setForeground(Color.BLACK); // Set teks menjadi hitam untuk kontras
            } else if (tanggalExpired.before(sevenDaysLater)) {
                // Within 7 days from expiration
                cellComponent.setBackground(Color.YELLOW); // Set background warna kuning muda
                cellComponent.setForeground(Color.BLACK); // Set teks menjadi hitam untuk kontras
            } else {
                cellComponent.setBackground(table.getBackground());
                cellComponent.setForeground(table.getForeground());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return cellComponent;
    }
}



public class ExpiredBarang extends javax.swing.JFrame {
    // Deklarasikan HashMap di dalam kelas
    private HashMap<String, Integer> supplierMap = new HashMap<>();

    private Connection conn = new helper.database().connect();
    database db = new database();
    private DefaultTableModel tabmode;
    reset reset = new reset();
    
    
    protected void datatable() {
    Object[] Baris = {"id_expired", "id_barang", "nama_barang", "tanggal_expired", "jumlah"};
    tabmode = new DefaultTableModel(null, Baris);
    tabExpiredBarang.setModel(tabmode);

    String sql = "SELECT be.id_expiredBarang, b.id_barang, b.nama_barang, be.tanggal_expired, be.jumlah FROM barang_expired AS be INNER JOIN barang AS b ON be.id_barang = b.id_barang";

    try {
        Statement stat = conn.createStatement();
        ResultSet res = stat.executeQuery(sql);
        while (res.next()) {
            String idExpired = res.getString("id_expiredBarang");
            String idBarang = res.getString("id_barang");
            String namaBarang = res.getString("nama_barang");
            String tanggalExpired = res.getString("tanggal_expired");
            String jumlahExpired = res.getString("jumlah");

            // Konversi tanggal_expired ke java.sql.Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date tanggalExpiredUtil = dateFormat.parse(tanggalExpired);
            java.sql.Date tanggalExpiredSql = new java.sql.Date(tanggalExpiredUtil.getTime());

            // Pengecekan apakah tanggal_expired sudah lewat
            if (tanggalExpiredSql.before(new java.util.Date())) {
                String expiredInfo = "ID Expired: " + idExpired + "\n"
                        + "ID Barang: " + idBarang + "\n"
                        + "Nama Barang: " + namaBarang + "\n"
                        + "Tanggal Expired: " + tanggalExpired + "\n"
                        + "Jumlah Expired: " + jumlahExpired;

                JOptionPane.showMessageDialog(this, expiredInfo, "Barang Expired", JOptionPane.WARNING_MESSAGE);
            }

            String[] data = {idExpired, idBarang, namaBarang, tanggalExpired, jumlahExpired};
            tabmode.addRow(data);
        }

        // Apply renderer khusus pada kolom "tanggal_expired"
        TableColumn tanggalExpiredColumn = tabExpiredBarang.getColumnModel().getColumn(3);
        tanggalExpiredColumn.setCellRenderer(new ExpiredRowRenderer());
    } catch (SQLException | ParseException ex) {
        Logger.getLogger(ExpiredBarang.class.getName()).log(Level.SEVERE, null, ex);
    }

}

    
     void reset(){
        reset.resetTextFields(this.getContentPane());
    }
    
     
     public ExpiredBarang() {
    initComponents();
    datatable();
   
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(jScrollPane1, BorderLayout.CENTER);
        pack();
}
     

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButton1 = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNamaBarang = new javax.swing.JTextField();
        txtIdBarang = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabExpiredBarang = new javax.swing.JTable();
        btnCariBarang = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtTglExpired = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtIdExpiredBarang = new javax.swing.JTextField();

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Data Expired Barang");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(483, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(437, 437, 437))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
        );

        jLabel2.setText("ID Barang");

        jLabel3.setText("Nama Barang");

        txtNamaBarang.setEnabled(false);
        txtNamaBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaBarangActionPerformed(evt);
            }
        });

        txtIdBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdBarangActionPerformed(evt);
            }
        });

        jButton5.setText("Cetak");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        tabExpiredBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabExpiredBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabExpiredBarangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabExpiredBarang);

        btnCariBarang.setText("Cari");
        btnCariBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariBarangActionPerformed(evt);
            }
        });

        jLabel4.setText("Tgl Expired");

        jLabel5.setText("Jumlah");

        txtJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahActionPerformed(evt);
            }
        });

        jLabel6.setText("ID Expired Barang");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnReset))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtIdBarang)
                                    .addComponent(txtNamaBarang)
                                    .addComponent(txtTglExpired, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                    .addComponent(txtIdExpiredBarang))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCariBarang)))
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 607, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtIdExpiredBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtIdBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCariBarang))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtTglExpired, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSave)
                            .addComponent(btnUpdate)
                            .addComponent(btnDelete)
                            .addComponent(btnReset)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addContainerGap(103, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
   
    private void txtNamaBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaBarangActionPerformed

    private void txtIdBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdBarangActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        int idBarang = Integer.parseInt(txtIdBarang.getText());
        java.sql.Date tanggalExpired = new java.sql.Date(txtTglExpired.getDate().getTime());
        int jumlahExpired = Integer.parseInt(txtJumlah.getText());

        String[] columns = {"id_barang", "tanggal_expired", "jumlah"};
        Object[] values = {idBarang, tanggalExpired, jumlahExpired};

        try {
            db.insertData(conn, "barang_expired", columns, values);
            JOptionPane.showMessageDialog(this, "Data barang expired berhasil ditambahkan");
            datatable(); // Panggil metode untuk mengisi ulang JTable barang_expired
            reset(); // Reset form input data barang_expired
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(this, "Gagal menambahkan data barang expired");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
         int selectedRow = tabExpiredBarang.getSelectedRow();
if (selectedRow != -1) {
    String idExpiredStr = tabExpiredBarang.getValueAt(selectedRow, 0).toString(); // Ganti indeks kolom sesuai dengan tabel Anda
    String idBarangStr = tabExpiredBarang.getValueAt(selectedRow, 1).toString(); // Ganti indeks kolom sesuai dengan tabel Anda

    int idExpired = Integer.parseInt(idExpiredStr);
    int idBarang = Integer.parseInt(idBarangStr);

    java.sql.Date tanggalExpired = new java.sql.Date(txtTglExpired.getDate().getTime());
    int jumlahExpired = Integer.parseInt(txtJumlah.getText());

    String[] columns = {"id_barang", "tanggal_expired", "jumlah"};
    Object[] values = {idBarang, tanggalExpired, jumlahExpired};

    try {
        db.updateData(conn, "barang_expired", columns, values, "id_expiredBarang = " + idExpired);
        JOptionPane.showMessageDialog(this, "Data barang expired berhasil diperbarui");
        datatable();
        reset();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Gagal memperbarui data barang expired");
    }
} else {
    JOptionPane.showMessageDialog(this, "Pilih data yang akan diperbarui");
}
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int idBarang = Integer.parseInt(txtIdExpiredBarang.getText());
        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                db.deleteData(conn, "barang_expired", "id_expiredBarang = " + idBarang);
                JOptionPane.showMessageDialog(this, "Expired Barang berhasil dihapus");
                datatable();
                reset();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus expired barang");
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        reset();
    }//GEN-LAST:event_btnResetActionPerformed
    
   
    private void tabExpiredBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabExpiredBarangMouseClicked
       int selectedRow = tabExpiredBarang.getSelectedRow();
        if (selectedRow != -1) {
            String idExpired = tabExpiredBarang.getValueAt(selectedRow, 0).toString();
            String idBarang = tabExpiredBarang.getValueAt(selectedRow, 1).toString();
            String namaBarang = tabExpiredBarang.getValueAt(selectedRow, 2).toString();
            String tanggalExpired = tabExpiredBarang.getValueAt(selectedRow, 3).toString();
            String jumlahExpired = tabExpiredBarang.getValueAt(selectedRow, 4).toString();
            
            txtNamaBarang.setText(namaBarang);
            txtIdBarang.setText(idBarang);
            txtIdExpiredBarang.setText(idExpired);
            txtTglExpired.setDate(java.sql.Date.valueOf(tanggalExpired));
            txtJumlah.setText(jumlahExpired);
            // Tambahkan kode untuk mengisi txtNamaSupplier, txtNamaBarang, txtStok, dan txtHarga di sini
        }
    
    }//GEN-LAST:event_tabExpiredBarangMouseClicked

    private void txtJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahActionPerformed

    private void btnCariBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariBarangActionPerformed
        String id_barang = txtIdBarang.getText();
        if (!id_barang.isEmpty()) {
                    try {
                        String query = "SELECT nama_barang FROM barang WHERE id_barang = ?";
                        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                            preparedStatement.setString(1, id_barang);
                            ResultSet resultSet = preparedStatement.executeQuery();
                            
                            if (resultSet.next()) {
                                String namaBarang = resultSet.getString("nama_barang");
                                txtNamaBarang.setText(namaBarang);
                            } else {
                                JOptionPane.showMessageDialog(ExpiredBarang.this, "Barang tidak ditemukan");
                            }
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(ExpiredBarang.this, "Isi terlebih dahulu ID Barang yang ingin dicari.");
                }
            
    
    }//GEN-LAST:event_btnCariBarangActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new ExpiredBarang().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCariBarang;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabExpiredBarang;
    private javax.swing.JTextField txtIdBarang;
    private javax.swing.JTextField txtIdExpiredBarang;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtNamaBarang;
    private com.toedter.calendar.JDateChooser txtTglExpired;
    // End of variables declaration//GEN-END:variables
}
