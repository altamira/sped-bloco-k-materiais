// title      : Coluna N3
// author     : Alessandro
// license    : MIT License
// revision   : 0.001
// tags       : cantoneira, N3
// file       : n3.jscad

function oblongo(line_offset, head, tail, radius) {
   //var o = Array.prototype.slice.call();
   return union(     // -- we have to extrude all, in order to union (we can't mix CAG and CSG)
      linear_extrude({height: 2},     // convex hulled extruded
         hull(circle({r: radius, center: true}).translate([line_offset, head, 0]), 
             circle({r: radius, center: true}).translate([line_offset, tail, 0])))
   );
}

function blank(largura, comprimento, espessura) {
    //var o = Array.prototype.slice.call(arguments);
    return cube([largura, comprimento, espessura]);
}

function oblongos(raio, tamanho, passo, comprimento) {
    
    var o1;

    var line_offset = [];
    
    /* cantoneira n3, setup da furação */
    line_offset[0] = {line: 15.5, offset: 9.5};   /* primeira fileira de oblongo */
    line_offset[1] = {line: 70.5, offset: 9.5};   /* segunda fileira de oblongo */
    line_offset[2] = {line: 50.5, offset: -10.5}; /* terceira fileira de oblongo */
    
    var count = 0;
    
    var obl =[];
    
    var u;
    
    for (x = 0; x < line_offset.length; x++) {
        if (line_offset[x].offset < 0) {
            count = Math.ceil((comprimento - line_offset[x].offset) / passo);
        } else {
            count = Math.ceil(comprimento / passo);
        }
        for (i = 0; i < count; i++) {
            obl[i] = oblongo(line_offset[x].line, line_offset[x].offset + (i * passo), line_offset[x].offset + (tamanho + (i * passo)), raio);
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
    {
      name: 'passo', 
      type: 'int', 
      initial: 40,
      caption: "Passo do oblongo:", 
    },
    {
      name: 'tamanho', 
      type: 'int', 
      initial: 21,
      caption: "Tamanho interno do oblongo:", 
    },
    {
      name: 'raio', 
      type: 'float', 
      initial: 4.5,
      caption: "Raio do oblongo:", 
    },
    {
      name: 'comprimento', 
      type: 'int', 
      initial: 1000,
      caption: "Comprimento da cantoneira N3:", 
    },
    {
      name: 'largura', 
      type: 'float', 
      initial: 85,
      caption: "Largura da bobina:", 
    },
    {
      name: 'espessura', 
      type: 'float', 
      initial: 2,
      caption: "Espessura da bobina:", 
    }
  ];
}

function main(params) {
    
    /* parametros dos oblongos */
    var passo = 40;
    var tamanho = 21;
    var raio = 4.5;
    
    /* parametros da coluna n3 */
    var comprimento = 1000;
    var largura = 85;
    var espessura = 2;
    
    var blk = blank(params.largura, params.comprimento, params.espessura);
    var obl = oblongos(params.raio, params.tamanho, params.passo, params.comprimento);
    
    var n3 = difference(blk, obl)
                .translate([(largura / 2) * -1, (comprimento / 2) * -1, 0])
                .scale(0.2);

    /*
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
    */
      
    return n3;
}
