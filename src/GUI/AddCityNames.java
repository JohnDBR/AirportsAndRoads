package GUI;

import airportsandroads.Graph;

/**
 *
 * @author da-vinci
 */
public class AddCityNames extends javax.swing.JFrame {

    Graph g;
    int state;
    
    public AddCityNames(Graph g) {
        initComponents();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.g = g;
        this.state = 0;
        this.Display_la.setText("Digite el nombre de la ciudad " + (state + 1) + ":");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Display_la = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        Name = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Display_la.setText("jLabel1");

        jButton1.setText("Siguiente");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Display_la)
                    .addComponent(Name)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(303, Short.MAX_VALUE)
                .addComponent(Display_la)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(69, 69, 69))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(state < g.getOrder() - 1){
            g.addNode(state);
            g.getNodes().get(state).name = Name.getText();
            state++;
            if(state == (g.getOrder() - 1)) jButton1.setText("Finalizar");
            this.Display_la.setText("Digite el nombre de la ciudad " + (state + 1) + ":");
        }else{
            new Matrix(g);
            this.dispose();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Display_la;
    private javax.swing.JTextField Name;
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
}
