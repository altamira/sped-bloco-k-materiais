// title      : Coluna N2, N3, N5
// author     : Alessandro
// license    : MIT License
// revision   : 0.001
// tags       : cantoneira, N2, N3, N5
// file       : cantoneira.jscad

function oblongo(line_offset, head, tail, radius) {
   //var o = Array.prototype.slice.call();
   return union(     // -- we have to extrude all, in order to union (we can't mix CAG and CSG)
      linear_extrude({height: 2},     // convex hulled extruded
         hull(circle({r: radius, center: true}).translate([line_offset, head, 0]), 
             circle({r: radius, center: true}).translate([line_offset, tail, 0])))
   );
}

function blank(params) {
    //var o = Array.prototype.slice.call(arguments);
    return cube([params.largura, params.comprimento, params.espessura]);
}

function oblongos(params) {
    
    var o1;

    var line_offset = [];

    OpenJsCad.log("Tipo de Cantoneira escolhida: " + params.largura); 
    
    if (params.largura < 85) {
        /* cantoneira n2, setup da furação */
        line_offset[0] = {line: 10, offset: 9.5};   /* primeira fileira de oblongo */
        line_offset[1] = {line: 30, offset: -10.5}; /* terceira fileira de oblongo */          
    } else if (params.largura < 125) {
        /* cantoneira n3, setup da furação */
        line_offset[0] = {line: 15.5, offset: 9.5};   /* primeira fileira de oblongo */
        line_offset[1] = {line: 50.5, offset: -10.5}; /* terceira fileira de oblongo */        
        line_offset[2] = {line: 70.5, offset: 9.5};   /* segunda fileira de oblongo */
    } else {
        /* cantoneira n5, setup da furação */
        line_offset[0] = {line: 11, offset: 9.5};   /* primeira fileira de oblongo */
        line_offset[1] = {line: 31, offset: -10.5}; /* terceira fileira de oblongo */        
        line_offset[2] = {line: 51, offset: 9.5};   /* segunda fileira de oblongo */        
        line_offset[3] = {line: 95, offset: -10.5}; /* terceira fileira de oblongo */        
        line_offset[4] = {line: 115, offset: 9.5};   /* segunda fileira de oblongo */        
    }

    var count = 0;
    
    var obl =[];
    
    var u;
    
    for (x = 0; x < line_offset.length; x++) {
        if (line_offset[x].offset < 0) {
            count = Math.ceil((params.comprimento - line_offset[x].offset) / params.passo);
        } else {
            count = Math.ceil(params.comprimento / params.passo);
        }
        for (i = 0; i < count; i++) {
            obl[i] = oblongo(line_offset[x].line, line_offset[x].offset + (i * params.passo), line_offset[x].offset + (params.tamanho + (i * params.passo)), params.raio);
        }
        
        if (!u) {
            u = union(obl);
        } else {
            u = u.union(obl);
        }
    }

    return u;
}

function getParameterDefinitions() {
  return [
    /* parametros dos oblongos */
    {
        name: 'passo', 
        type: 'int', 
        initial: 40,
        caption: "Passo do oblongo (mm):", 
    },
    {
        name: 'tamanho', 
        type: 'int', 
        initial: 21,
        caption: "Tamanho interno do oblongo (mm):", 
    },
    {
        name: 'raio', 
        type: 'float', 
        initial: 4.5,
        caption: "Raio do oblongo (mm):", 
    },
    /* parametros da coluna n3 */
    {
        name: 'comprimento', 
        type: 'int', 
        initial: 360,
        caption: "Comprimento da cantoneira (mm):", 
    },
    { 
        name: 'largura', 
        type: 'choice', 
        caption: 'Tipo de cantoneira:', 
        values: [66, 85, 125], 
        captions: ["N2", "N3", "N5"], 
        initial: 85 },
    {
        name: 'espessura', 
        type: 'float', 
        initial: 1.8,
        caption: "Espessura da chapa (mm):", 
    }
  ];
}

function main(params) {

    var blk = blank(params);
    var obl = oblongos(params);
    
    var n3 = difference(blk, obl)
                .translate([(params.largura / 2) * -1, (params.comprimento / 2) * -1, 0])
                .scale(0.2);

    /* propriedades fisicas */
    var features = n3.getFeatures(["volume","area"]);
    var volume = features[0];
    var area = features[1];
    
    OpenJsCad.log("N3 -> volume: "+Math.ceil(volume)+"; area: "+area);   // volume: 8; area: 24 
    
    features = blk.getFeatures(["volume","area"]);
    volume = features[0];
    area = features[1];
    
    OpenJsCad.log("blank -> volume: "+Math.ceil(volume)+"; area: "+area);   // volume: 8; area: 24 
    
    features = obl.getFeatures(["volume","area"]);
    volume = features[0];
    area = features[1];
    
    OpenJsCad.log("oblongo -> volume: "+Math.ceil(volume)+"; area: "+area);   // volume: 8; area: 24 

    return n3;
}
